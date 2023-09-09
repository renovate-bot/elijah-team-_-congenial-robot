/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.Deduce_CreationClosure;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Holder;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created 7/3/21 6:24 AM
 */
public class WlGenerateCtor implements WorkJob {
	private final @Nullable IdentExpression    constructorName;
	private final           ICodeRegistrar     codeRegistrar;
	private final @NotNull  GenerateFunctions  generateFunctions;
	private final @NotNull  FunctionInvocation functionInvocation;
	private                 boolean            _isDone = false;
	private                 EvaConstructor     result;

	@Contract(pure = true)
	public WlGenerateCtor(@NotNull GenerateFunctions aGenerateFunctions,
						  @NotNull FunctionInvocation aFunctionInvocation,
						  @Nullable IdentExpression aConstructorName,
						  final ICodeRegistrar aCodeRegistrar) {
		generateFunctions  = aGenerateFunctions;
		functionInvocation = aFunctionInvocation;
		constructorName    = aConstructorName;
		codeRegistrar      = aCodeRegistrar;
	}

	public WlGenerateCtor(final OS_Module aModule, final IdentExpression aNameNode, final FunctionInvocation aFunctionInvocation, final @NotNull Deduce_CreationClosure aCl) {
		this(aCl.generatePhase().getGenerateFunctions(aModule), aFunctionInvocation, aNameNode, aCl.generatePhase().getCodeRegistrar());
	}

	private boolean getPragma(String aAuto_construct) {
		return false;
	}

	public EvaConstructor getResult() {
		return result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		if (functionInvocation.generateDeferred().isPending()) {
			final ClassStatement klass     = functionInvocation.getClassInvocation().getKlass();
			Holder<EvaClass>     hGenClass = new Holder<>();
			functionInvocation.getClassInvocation().resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(EvaClass result) {
					hGenClass.set(result);
				}
			});
			EvaClass genClass = hGenClass.get();
			assert genClass != null;

			ConstructorDef ccc = null;
			if (constructorName != null) {
				Collection<ConstructorDef> cs = klass.getConstructors();
				for (@NotNull ConstructorDef c : cs) {
					if (c.name().sameName(constructorName.getText())) {
						ccc = c;
						break;
					}
				}
			}

			ConstructorDef cd;
			if (ccc == null) {
				cd = new ConstructorDefImpl(constructorName, (_CommonNC) klass, klass.getContext());
				Scope3Impl scope3 = new Scope3Impl(cd);
				cd.scope(scope3);
				for (EvaContainer.VarTableEntry varTableEntry : genClass.varTable) {
					if (varTableEntry.initialValue != IExpression.UNASSIGNED) {
						IExpression left  = varTableEntry.nameToken;
						IExpression right = varTableEntry.initialValue;

						IExpression e = ExpressionBuilder.build(left, ExpressionKind.ASSIGNMENT, right);
						scope3.add(new StatementWrapperImpl(e, cd.getContext(), cd));
					} else {
						if (true) {
							scope3.add(new ConstructStatementImpl(cd, cd.getContext(), varTableEntry.nameToken, null, null));
						}
					}
				}
			} else
				cd = ccc;

			OS_Element classStatement_ = cd.getParent();
			assert classStatement_ instanceof ClassStatement;

			ClassStatement             classStatement = (ClassStatement) classStatement_;
			Collection<ConstructorDef> cs             = classStatement.getConstructors();
			ConstructorDef             c              = null;
			if (constructorName != null) {
				for (ConstructorDef cc : cs) {
					if (cc.name().sameName(constructorName.getText())) {
						c = cc;
						break;
					}
				}
			} else {
				// TODO match based on arguments
				ProcTableEntry       pte  = functionInvocation.pte;
				List<TypeTableEntry> args = pte.getArgs();
				// isResolved -> GeneratedNode, etc or getAttached -> OS_Element
				for (ConstructorDef cc : cs) {
					Collection<FormalArgListItem> cc_args = cc.getArgs();
					if (cc_args.size() == args.size()) {
						if (args.size() == 0) {
							c = cc;
							break;
						}
						int y = 2;
					}
				}
			}

			{
				// TODO what about multiple inheritance?

				// add inherit statement, if any

				// add code from c
				if (c != null && c != cd) {
					ArrayList<FunctionItem> is = new ArrayList<>(c.getItems());

					// skip initializers (already present in cd)
//				FunctionItem firstElement = is.get(0);
//				if (firstElement instanceof InheritStatement) {
//					cd.insertInherit(firstElement);
//					is.remove(0);
//				}

					for (FunctionItem item : is) {
						cd.add(item);
					}
				}
			}

			@NotNull EvaConstructor gf = generateFunctions.generateConstructor(cd, (ClassStatement) classStatement_, functionInvocation);
//		lgf.add(gf);

			final ClassInvocation ci = functionInvocation.getClassInvocation();
			ci.resolvePromise().done(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(@NotNull EvaClass result) {

					codeRegistrar.registerFunction1(gf);
					//gf.setCode(generateFunctions.module.getCompilation().nextFunctionCode());

					gf.setClass(result);
					result.constructors.put(cd, gf);
				}
			});

			functionInvocation.generateDeferred().resolve(gf);
			functionInvocation.setGenerated(gf);

			result = gf;
		}

		_isDone = true;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

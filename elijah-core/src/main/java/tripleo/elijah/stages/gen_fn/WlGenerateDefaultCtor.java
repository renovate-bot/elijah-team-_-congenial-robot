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

import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.Deduce_CreationClosure;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Holder;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

/**
 * Created 5/31/21 2:26 AM
 */
public class WlGenerateDefaultCtor implements WorkJob {
	private final          FunctionInvocation    functionInvocation;
	private final @NotNull GenerateFunctions     generateFunctions;
	private                boolean               _isDone = false;
	private                BaseEvaFunction       Result;
	private                DeduceCreationContext dcc;
	private final          ICodeRegistrar        codeRegistrar;

	@Contract(pure = true)
	public WlGenerateDefaultCtor(@NotNull GenerateFunctions aGenerateFunctions, FunctionInvocation aFunctionInvocation, DeduceCreationContext aDcc, final ICodeRegistrar aCodeRegistrar) {
		generateFunctions  = aGenerateFunctions;
		functionInvocation = aFunctionInvocation;
		dcc                = aDcc;
		codeRegistrar      = aCodeRegistrar;
	}

	public WlGenerateDefaultCtor(final OS_Module module, final FunctionInvocation aFunctionInvocation, final @NotNull Deduce_CreationClosure crcl) {
		this(crcl.generatePhase().getGenerateFunctions(module), aFunctionInvocation, crcl.dcc(), crcl.deducePhase().getCodeRegistrar());
	}

	private boolean getPragma(String aAuto_construct) {
		return false;
	}

	public BaseEvaFunction getResult() {
		return Result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		if (functionInvocation.generateDeferred().isPending()) {
			final ClassStatement klass     = functionInvocation.getClassInvocation().getKlass();
			functionInvocation.getClassInvocation().resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(EvaClass genClass) {
					assert Result == null;
					Result = xx(klass, genClass);
					_isDone = true;
				}
			});
		} else {
			functionInvocation.generatePromise().then(new DoneCallback<BaseEvaFunction>() {
				@Override
				public void onDone(final BaseEvaFunction result) {
					Result = result;
					//_isDone = true;

				}
			});
		}

		//_isDone = true;
		_isDone = true;
	}

	@NotNull
	private EvaConstructor xx(final ClassStatement klass, final EvaClass genClass) {
		ConstructorDef cd = new ConstructorDefImpl(null, (_CommonNC) klass, klass.getContext());
		cd.setName(LangGlobals.emptyConstructorName);
		Scope3Impl scope3 = new Scope3Impl(cd);
		cd.scope(scope3);
		for (EvaContainer.VarTableEntry varTableEntry : genClass.varTable) {
			if (varTableEntry.initialValue != IExpression.UNASSIGNED) {
				IExpression left  = varTableEntry.nameToken;
				IExpression right = varTableEntry.initialValue;

				IExpression e = ExpressionBuilder.build(left, ExpressionKind.ASSIGNMENT, right);
				scope3.add(new WrappedStatementWrapper(e, cd.getContext(), cd, (VariableStatementImpl) varTableEntry.vs()));
			} else {
				if (true) {
					scope3.add(new ConstructStatementImpl(cd, cd.getContext(), varTableEntry.nameToken, null, null));
				}
			}
		}

		OS_Element classStatement = cd.getParent();
		assert classStatement instanceof ClassStatement;
		@NotNull EvaConstructor gf = generateFunctions.generateConstructor(cd, (ClassStatement) classStatement, functionInvocation);
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
		return gf;
	}
}

//
//
//

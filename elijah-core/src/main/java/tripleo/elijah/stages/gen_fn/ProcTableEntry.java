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
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.IDeduceElement3;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.ProcIA;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created 9/12/20 10:07 PM
 */
public class ProcTableEntry extends BaseTableEntry implements TableEntryIV {
	public final @NotNull List<TypeTableEntry> args;
	/**
	 * Either a hint to the programmer-- The compiler should be able to work without this.
	 * <br/>
	 * Or for synthetic methods
	 */
	public final IExpression          __debug_expression;
	public final InstructionArgument  expression_num;

	public final @NotNull EvaExpression<IExpression> expression;


	public final    int                                             index;
	private final   DeferredObject<Ok, Void, Void>                  _p_completeDeferred      = new DeferredObject<Ok, Void, Void>();
	private final   DeferredObject2<FunctionInvocation, Void, Void> _p_onFunctionInvocations = new DeferredObject2<FunctionInvocation, Void, Void>();
	public @NotNull DeduceProcCall                                  dpc                      = new DeduceProcCall(this);
	@NotNull        ExpressionConfession                            expressionConfession     = new ExpressionConfession() {
		@Override
		public @NotNull ECT getType() {
			return ECT.exp;
		}
	};
	private         DeduceElement3_ProcTableEntry                   _de3;
	private         ClassInvocation                                 classInvocation;
	private         FunctionInvocation                              functionInvocation;

	public ProcTableEntry(final int aIndex, final IExpression aExpression, final @Nullable InstructionArgument aExpressionNum, final List<TypeTableEntry> aArgs) {
		index              = aIndex;
		__debug_expression = aExpression;
		expression_num     = aExpressionNum;

//		expressionConfession = ExpressionConfession.from(expression, expression_num);

		if (aArgs.size() == 0) {
			args = Collections.emptyList();
		} else {
			args = aArgs;
		}

		addStatusListener(new _StatusListener_PTE_67());

		setupResolve();
		if (aExpressionNum != null) {
			if (aExpressionNum instanceof IdentIA identIA) {
				expression = new EvaExpression<>(__debug_expression, identIA.getEntry());
			} else if (aExpressionNum instanceof IntegerIA integerIA) {
				expression = new EvaExpression<>(__debug_expression, integerIA.getEntry());
			} else if (aExpressionNum instanceof ProcIA procIA) {
				expression = new EvaExpression<>(__debug_expression, procIA.getEntry());
			} else
				throw new Error();
		} else {
			expression = new EvaExpression<>(__debug_expression, this); // FIXME justify this
		}
	}

	public void onSetAttached() {
		int state = 0;
		if (/*args != null ||*/ args != Collections.EMPTY_LIST) {
			final int ac  = args.size();
			int       acx = 0;
			for (TypeTableEntry tte : args) {
				if (tte.getAttached() != null)
					acx++;
			}
			if (acx < ac) {
				state = 1;
			} else if (acx > ac) {
				state = 2;
			} else if (acx == ac) {
				state = 3;
			}
		} else {
			state = 3;
		}
		switch (state) {
		case 0:
			throw new IllegalStateException();
		case 1:
			SimplePrintLoggerToRemoveSoon.println_err_2("136 pte not finished resolving " + this);
			break;
		case 2:
			SimplePrintLoggerToRemoveSoon.println_err_2("138 Internal compiler error");
			break;
		case 3:
			if (_p_completeDeferred.isPending())
				_p_completeDeferred.resolve(Ok.instance());
			break;
		default:
			throw new NotImplementedException();
		}
	}

	private @NotNull DeferredObject<Ok, Void, Void> completeDeferred() {
		return _p_completeDeferred;
	}

	public DeduceProcCall deduceProcCall() {
		return dpc;
	}

	public @NotNull ExpressionConfession expressionConfession() {
		if (expressionConfession == null) {
			if (expression_num == null) {
				expressionConfession = new ExpressionConfession() {
					@Override
					public @NotNull ECT getType() {
						return ECT.exp;
					}
				};
			} else {
				expressionConfession = new ExpressionConfession() {
					@Override
					public @NotNull ECT getType() {
						return ECT.exp_num;
					}
				};
			}
		}

		return expressionConfession;
	}

	public ClassInvocation getClassInvocation() {
		return classInvocation;
	}

	public void setClassInvocation(ClassInvocation aClassInvocation) {
		classInvocation = aClassInvocation;
	}

	public IDeduceElement3 getDeduceElement3() {
		//assert dpc._deduceTypes2() != null; // TODO setDeduce... called; Promise?
		//
		//return getDeduceElement3(dpc._deduceTypes2(), dpc._generatedFunction());

		return getDeduceElement3(_deduceTypes2(), __gf);
	}

	public DeduceTypes2 _deduceTypes2() {
		return __dt2;
	}

	public @NotNull IDeduceElement3 getDeduceElement3(final @NotNull DeduceTypes2 aDeduceTypes2,
													  final @NotNull BaseEvaFunction aGeneratedFunction) {
		if (_de3 == null) {
			_de3 = new DeduceElement3_ProcTableEntry(this, aDeduceTypes2, aGeneratedFunction);
		}
		return _de3;
	}

	public FunctionInvocation getFunctionInvocation() {
		return functionInvocation;
	}

	// have no idea what this is for
	public void setFunctionInvocation(FunctionInvocation aFunctionInvocation) {
		if (functionInvocation != aFunctionInvocation) {
			functionInvocation = aFunctionInvocation;
			_p_onFunctionInvocations.reset();
			_p_onFunctionInvocations.resolve(functionInvocation);
		}
	}

	@NotNull
	public String getLoggingString(final @Nullable DeduceTypes2 aDeduceTypes2) {
		final String          pte_string;
		@NotNull List<String> l = new ArrayList<String>();

		for (@NotNull TypeTableEntry typeTableEntry : getArgs()) {
			OS_Type attached = typeTableEntry.getAttached();

			if (attached != null)
				l.add(attached.toString());
			else {
				if (aDeduceTypes2 != null)
					aDeduceTypes2.LOG.err("267 attached == null for " + typeTableEntry);

				if (typeTableEntry.__debug_expression != null)
					l.add(String.format("<Unknown expression: %s>", typeTableEntry.__debug_expression));
				else
					l.add("<Unknkown>");
			}
		}

		final String sb2 = "[" +
				Helpers.String_join(", ", l) +
				"]";
		pte_string = sb2;
		return pte_string;
	}

	public List<TypeTableEntry> getArgs() {
		return args;
	}

	// have no idea what this is for
	public void onFunctionInvocation(final DoneCallback<FunctionInvocation> callback) {
		_p_onFunctionInvocations.then(callback);
	}

	public void setDeduceTypes2(final DeduceTypes2 aDeduceTypes2, final Context aContext, final BaseEvaFunction aGeneratedFunction, final ErrSink aErrSink) {
		dpc.setDeduceTypes2(aDeduceTypes2, aContext, aGeneratedFunction, aErrSink);
	}

	public void setArgType(int aIndex, OS_Type aType) {
		args.get(aIndex).setAttached(aType);
	}

	public void setExpressionConfession(final @NotNull ExpressionConfession aExpressionConfession) {
		expressionConfession = aExpressionConfession;
	}

	@Override
	@NotNull
	public String toString() {
		return "ProcTableEntry{" +
				"index=" + index +
				", expression=" + __debug_expression +
				", expression_num=" + expression_num +
				", status=" + status +
				", args=" + args +
				'}';
	}

	public void resolveType(final GenType aResult) {
		if (typeDeferred().isResolved()) {
			//typeDeferred().reset(); // !! 07/20
			return; // README 11/10
		}
		typeDeferred().resolve(aResult);
	}

	public Eventual<GenType> typeDeferred() {
		return typeResolve.typeResolution();
	}

	public Eventual<GenType> typePromise() {
		return typeResolvePromise();
	}

	public EvaExpression<IExpression> getEvaExpression() {
		return expression;
	}

	public DeduceTypes2.DeduceTypes2Injector _inj() {
		return _deduceTypes2()._inj();
	}

	public void typePromise_setRegister(final EventualRegister aRegister) {
		typePromise();
	}

	public enum ECT {exp, exp_num}

	private class _StatusListener_PTE_67 implements StatusListener {
		@Override
		public void onChange(/*@NotNull*/ IElementHolder eh, Status newStatus) {
			if (newStatus == Status.KNOWN) {
				setResolvedElement(eh.getElement());
			}
		}
	}

	//public PTE_Zero zero() {
	//	if (_zero == null)
	//		_zero = new PTE_Zero(this);
	//
	//	return _zero;
	//}
}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.FunctionContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.lang.impl.LookupResultListImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang.types.OS_UnknownType;
import tripleo.elijah.lang2.BuiltInTypes;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.IDeduceElement3;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.Collections;
import java.util.Objects;
import java.util.Stack;

/**
 * Created 3/7/21 1:13 AM
 */
public enum DeduceLookupUtils {
	;

	@Nullable
	public static OS_Element _resolveAlias(final @NotNull AliasStatement aliasStatement, @NotNull DeduceTypes2 deduceTypes2) {
		try {
			return _resolveAlias2(aliasStatement, deduceTypes2);
		} catch (ResolveError aE) {
			aE.printStackTrace();
			return null;
		}
	}

	@Nullable
	public static OS_Element _resolveAlias2(final @NotNull AliasStatement best, @NotNull DeduceTypes2 deduceTypes2) throws ResolveError {
		LookupResultList lrl2;
		if (best.getExpression() instanceof Qualident) {
			final IExpression de = Helpers.qualidentToDotExpression2(((Qualident) best.getExpression()));
			if (de instanceof DotExpression) {
				lrl2 = lookup_dot_expression(best.getContext(), (DotExpression) de, deduceTypes2);
			} else
				lrl2 = best.getContext().lookup(((IdentExpression) de).getText());
			return lrl2.chooseBest(null);
		}
		// TODO what about when DotExpression is not just simple x.y.z? then alias equivalent to val
		if (best.getExpression() instanceof DotExpression) {
			final IExpression de = best.getExpression();
			lrl2 = lookup_dot_expression(best.getContext(), (DotExpression) de, deduceTypes2);
			return lrl2.chooseBest(null);
		}
		lrl2 = best.getContext().lookup(((IdentExpression) best.getExpression()).getText());
		return lrl2.chooseBest(null);
	}

	public static @Nullable GenType deduceExpression(@NotNull DeduceTypes2 aDeduceTypes2, @NotNull final IExpression n, final @NotNull Context context) throws ResolveError {
		switch (n.getKind()) {
		case IDENT:
			return deduceIdentExpression(aDeduceTypes2, (IdentExpression) n, context);
		case NUMERIC:
			final @NotNull GenType genType = aDeduceTypes2._inj().new_GenTypeImpl();
			genType.setResolved(aDeduceTypes2._inj().new_OS_BuiltinType(BuiltInTypes.SystemInteger));
			return genType;
		case DOT_EXP:
			final @NotNull DotExpression de = (DotExpression) n;
			final LookupResultList lrl = lookup_dot_expression(context, de, aDeduceTypes2);
			final @Nullable GenType left_type = deduceExpression(aDeduceTypes2, de.getLeft(), context);
			final @Nullable GenType right_type = deduceExpression(aDeduceTypes2, de.getRight(), left_type.getResolved().getClassOf().getContext());
			NotImplementedException.raise();
			break;
		case PROCEDURE_CALL:
			@Nullable GenType ty = deduceProcedureCall((ProcedureCallExpression) n, context, aDeduceTypes2);
			return ty/*n.getType()*/;
		case QIDENT:
			final IExpression expression = Helpers.qualidentToDotExpression2(((Qualident) n));
			return deduceExpression(aDeduceTypes2, expression, context);
		default:
			return null;
		}

		throw new IllegalStateException("Error");
	}

	@Contract("_, _ -> param1")
	public static @NotNull DeduceElement3_IdentTableEntry deduceExpression2(final @NotNull DeduceElement3_IdentTableEntry de3_ite, final FunctionContext aFc) {
		final IdentExpression identExpression = de3_ite.principal.getIdent();
		final IdentTableEntry ite             = de3_ite.principal._deduceTypes2()._inj().new_IdentTableEntry(0, identExpression, identExpression.getContext(), de3_ite.generatedFunction);

		try {
			deduceIdentExpression2(de3_ite);
		} catch (ResolveError aE) {
			throw new RuntimeException(aE);
		}

		return de3_ite;
	}

	private static @Nullable GenType deduceIdentExpression(@NotNull DeduceTypes2 aDeduceTypes2, final @NotNull IdentExpression ident, final @NotNull Context ctx) throws ResolveError {
		@Nullable GenType result = null;
		@Nullable GenType R      = aDeduceTypes2._inj().new_GenTypeImpl();

		// is this right?
		LookupResultList     lrl  = ctx.lookup(ident.getText());
		@Nullable OS_Element best = lrl.chooseBest(null);
		while (best instanceof AliasStatementImpl) {
			best = _resolveAlias2((AliasStatementImpl) best, aDeduceTypes2);
		}
		if (best instanceof ClassStatement) {
			R.setResolved(((ClassStatement) best).getOS_Type());
			result = R;
		} else {
			if (best != null)
				switch (DecideElObjectType.getElObjectType(best)) {
				case VAR:
					final @Nullable VariableStatementImpl vs = (VariableStatementImpl) best;
					if (!vs.typeName().isNull()) {
						try {
							@Nullable OS_Module lets_hope_we_dont_need_this = null;
							@NotNull GenType    ty                          = aDeduceTypes2.resolve_type(lets_hope_we_dont_need_this, aDeduceTypes2._inj().new_OS_UserType(vs.typeName()), ctx);
							result = ty;
						} catch (ResolveError aResolveError) {
							// TODO This is the cheap way to do it
							//  Ideally, we would propagate this up the call chain all the way to lookupExpression
							aResolveError.printStackTrace();
						}
						if (result == null) {
							R.setTypeName(aDeduceTypes2._inj().new_OS_UserType(vs.typeName()));
							result = R;
						}
					} else if (vs.initialValue() == IExpression.UNASSIGNED) {
						R.setTypeName(aDeduceTypes2._inj().new_OS_UnknownType(vs));
//				return deduceExpression(vs.initialValue(), ctx); // infinite recursion
					} else {
						final IExpression initialValue = vs.initialValue();
						if (Objects.requireNonNull(initialValue.getKind()) == ExpressionKind.PROCEDURE_CALL) {
							final Context                 vsContext = vs.getContext();
							final ProcedureCallExpression pce       = (ProcedureCallExpression) initialValue;
							R = deduceExpression(aDeduceTypes2, pce, vsContext);
						} else {
							R = deduceExpression(aDeduceTypes2, initialValue, vs.getContext());
						}
					}
					if (result == null) {
						result = R;
					}
					break;
				case FUNCTION:
					final @NotNull FunctionDef functionDef = (FunctionDef) best;
					R.setResolved(functionDef.getOS_Type());
					result = R;
					break;
				case FORMAL_ARG_LIST_ITEM:
					final @NotNull FormalArgListItem fali = (FormalArgListItem) best;
					if (!fali.typeName().isNull()) {
						try {
							@Nullable OS_Module lets_hope_we_dont_need_this = null;
							@NotNull GenType    ty                          = aDeduceTypes2.resolve_type(lets_hope_we_dont_need_this, aDeduceTypes2._inj().new_OS_UserType(fali.typeName()), ctx);
							result = ty;
						} catch (ResolveError aResolveError) {
							// TODO This is the cheap way to do it
							//  Ideally, we would propagate this up the call chain all the way to lookupExpression
							aResolveError.printStackTrace();
						}
						if (result == null) {
							R.setTypeName(aDeduceTypes2._inj().new_OS_UserType(fali.typeName()));
						}
					} else {
						R.setTypeName(aDeduceTypes2._inj().new_OS_UnknownType(fali));
					}
					if (result == null) {
						result = R;
					}
					break;
				default:
					throw new IllegalStateException("Error");
				}
			if (result == null) {
				throw new ResolveError(ident, lrl);
			}
		}
		return result;
	}

	private static void deduceIdentExpression2(final @NotNull IDeduceElement3 aDeduceElement3) throws ResolveError {
		@Nullable GenType result = null;
		@Nullable GenType R      = aDeduceElement3.genType();

		final @NotNull DeduceTypes2 dt2 = aDeduceElement3.deduceTypes2();
		//assert dt2 == aDeduceTypes2;

		final @NotNull IdentExpression ident = ((DeduceElement3_IdentTableEntry) aDeduceElement3).principal.getIdent();
		final Context                  ctx   = ident.getContext();

		// is this right?
		LookupResultList     lrl  = ctx.lookup(ident.getText());
		@Nullable OS_Element best = lrl.chooseBest(null);
		while (best instanceof AliasStatementImpl) {
			best = _resolveAlias2((AliasStatementImpl) best, dt2);
		}
		if (best instanceof ClassStatement) {
			R.setResolved(((ClassStatement) best).getOS_Type());
			result = R;
		} else {
			switch (DecideElObjectType.getElObjectType(best)) {
			case VAR:
				final @Nullable VariableStatementImpl vs = (VariableStatementImpl) best;
				if (!vs.typeName().isNull()) {
					try {
						@Nullable OS_Module lets_hope_we_dont_need_this = null;
						@NotNull GenType    ty                          = dt2.resolve_type(lets_hope_we_dont_need_this, dt2._inj().new_OS_UserType(vs.typeName()), ctx);
						result = ty;
					} catch (ResolveError aResolveError) {
						// TODO This is the cheap way to do it
						//  Ideally, we would propagate this up the call chain all the way to lookupExpression
						aResolveError.printStackTrace();
					}
					if (result == null) {
						R.setTypeName(dt2._inj().new_OS_UserType(vs.typeName()));
						result = R;
					}
				} else if (vs.initialValue() == IExpression.UNASSIGNED) {
					R.setTypeName(dt2._inj().new_OS_UnknownType(vs));
//				return deduceExpression(vs.initialValue(), ctx); // infinite recursion
				} else {
					final IExpression initialValue = vs.initialValue();
					if (Objects.requireNonNull(initialValue.getKind()) == ExpressionKind.PROCEDURE_CALL) {
						final Context                 vsContext = vs.getContext();
						final ProcedureCallExpression pce       = (ProcedureCallExpression) initialValue;
						R = deduceExpression(dt2, pce, vsContext);
					} else {
						R = deduceExpression(dt2, initialValue, vs.getContext());
					}
				}
				if (result == null) {
					result = R;
				}
				break;
			case FUNCTION:
				final @NotNull FunctionDef functionDef = (FunctionDef) best;
				R.setResolved(functionDef.getOS_Type());
				result = R;
				break;
			case FORMAL_ARG_LIST_ITEM:
				final @NotNull FormalArgListItem fali = (FormalArgListItem) best;
				if (!fali.typeName().isNull()) {
					try {
						@Nullable OS_Module lets_hope_we_dont_need_this = null;
						@NotNull GenType    ty                          = dt2.resolve_type(lets_hope_we_dont_need_this, dt2._inj().new_OS_UserType(fali.typeName()), ctx);
						result = ty;
					} catch (ResolveError aResolveError) {
						// TODO This is the cheap way to do it
						//  Ideally, we would propagate this up the call chain all the way to lookupExpression
						aResolveError.printStackTrace();
					}
					if (result == null) {
						R.setTypeName(dt2._inj().new_OS_UserType(fali.typeName()));
					}
				} else {
					R.setTypeName(dt2._inj().new_OS_UnknownType(fali));
				}
				if (result == null) {
					result = R;
				}
				break;
			default:
				throw new IllegalStateException("Error");
			}
			if (result == null) {
				throw new ResolveError(ident, lrl);
			}
		}
	}

	/**
	 * Try to find the type of a ProcedureCall. Will either be a constructor or function call, most likely
	 *
	 * @param pce          the procedure call
	 * @param ctx          the context to use for lookup
	 * @param deduceTypes2
	 * @return the deduced type or {@code null}. Do not {@code pce.setType}
	 */
	private static @Nullable GenType deduceProcedureCall(final @NotNull ProcedureCallExpression pce, final @NotNull Context ctx, @NotNull DeduceTypes2 deduceTypes2) {
		@Nullable GenType result   = deduceTypes2._inj().new_GenTypeImpl();
		boolean           finished = false;
		SimplePrintLoggerToRemoveSoon.println_err_2("979 During deduceProcedureCall " + pce);
		@Nullable OS_Element best = null;
		try {
			best = lookup(pce.getLeft(), ctx, deduceTypes2);
		} catch (ResolveError aResolveError) {
			finished = true;// TODO should we log this?
		}
		if (!finished) {
			if (best != null) {
				int y = 2;
				if (best instanceof ClassStatement) {
					result.setResolved(((ClassStatement) best).getOS_Type());
				} else if (best instanceof final @Nullable FunctionDef fd) {
					if (fd.returnType() != null && !fd.returnType().isNull()) {
						result.setResolved(deduceTypes2._inj().new_OS_UserType(fd.returnType()));
					} else {
						result.setResolved(deduceTypes2._inj().new_OS_UnknownType(fd));// TODO still must register somewhere
					}
				} else if (best instanceof final @NotNull FuncExpr funcExpr) {
					if (funcExpr.returnType() != null && !funcExpr.returnType().isNull()) {
						result.setResolved(deduceTypes2._inj().new_OS_UserType(funcExpr.returnType()));
					} else {
						result.setResolved(deduceTypes2._inj().new_OS_UnknownType(funcExpr));// TODO still must register somewhere
					}
				} else {
					SimplePrintLoggerToRemoveSoon.println_err_2("992 " + best.getClass().getName());
					throw new NotImplementedException();
				}
			}
		}
		return result;
	}

	/**
	 * @param de The {@link DotExpression} to turn into a {@link Stack}
	 * @return a "flat" {@link Stack<IExpression>} of expressions
	 * @see {@link tripleo.elijah.stages.deduce.DotExpressionToStackTest}
	 */
	@NotNull
	static Stack<IExpression> dot_expression_to_stack(final @NotNull DotExpression de) {
		final @NotNull Stack<IExpression> right_stack = new Stack<IExpression>();
		IExpression                       right       = de.getRight();
		right_stack.push(de.getLeft());
		while (right instanceof DotExpression) {
			right_stack.push(right.getLeft());
			right = ((DotExpression) right).getRight();
		}
		right_stack.push(right);
		Collections.reverse(right_stack);
		return right_stack;
	}

	static @Nullable OS_Element lookup(@NotNull IExpression expression, @NotNull Context ctx, @NotNull DeduceTypes2 deduceTypes2) throws ResolveError {
		switch (expression.getKind()) {
		case IDENT:
			LookupResultList lrl = ctx.lookup(((IdentExpression) expression).getText());
			@Nullable OS_Element best = lrl.chooseBest(null);
			return best;
		case PROCEDURE_CALL:
			LookupResultList lrl2 = lookupExpression(expression.getLeft(), ctx, deduceTypes2);
			@Nullable OS_Element best2 = lrl2.chooseBest(null);
			return best2;
		case DOT_EXP:
			LookupResultList lrl3 = lookupExpression(expression, ctx, deduceTypes2);
			@Nullable OS_Element best3 = lrl3.chooseBest(null);
			return best3;
//		default:
//			tripleo.elijah.util.Stupidity.println_err_2("1242 "+expression);
//			throw new NotImplementedException();
		default:
			throw new IllegalStateException("1242 Unexpected value: " + expression.getKind());
		}
	}

	private static LookupResultList lookup_dot_expression(Context ctx, final @NotNull DotExpression de, @NotNull DeduceTypes2 deduceTypes2) throws ResolveError {
		final @NotNull Stack<IExpression> s  = dot_expression_to_stack(de);
		@Nullable GenType                 t  = null;
		IExpression                       ss = s.peek();
		while (/*!*/s.size() > 1/*isEmpty()*/) {
			ss = s.peek();
			if (t != null) {
				final OS_Type resolved = t.getResolved();
				if (resolved != null && (resolved.getType() == OS_Type.Type.USER_CLASS || resolved.getType() == OS_Type.Type.FUNCTION))
					ctx = resolved.getClassOf().getContext();
			}
			t = deduceExpression(deduceTypes2, ss, ctx);
			if (t == null) break;
			s.pop();
		}
		{
//			s.pop();
			ss = s.peek();
		}
		if (t == null) {
			NotImplementedException.raise();
			return new LookupResultListImpl(); // TODO throw ResolveError
		} else {
			if (t.getResolved() instanceof OS_UnknownType)
				return new LookupResultListImpl(); // TODO is this right??
			if (t.isNull())
				return new LookupResultListImpl(); // TODO throw here? 06/26
			final LookupResultList lrl = t.getResolved().getElement()/*.getParent()*/.getContext().lookup(((IdentExpression) ss).getText());
			return lrl;
		}
	}

	public static LookupResultList lookupExpression(final @NotNull IExpression left,
													final @NotNull Context ctx,
													final @NotNull DeduceTypes2 deduceTypes2) throws ResolveError {
		switch (left.getKind()) {
		case QIDENT:
			final IExpression de = Helpers.qualidentToDotExpression2((Qualident) left);
			return lookupExpression(de, ctx, deduceTypes2)/*lookup_dot_expression(ctx, de)*/;
		case DOT_EXP:
			return lookup_dot_expression(ctx, (DotExpression) left, deduceTypes2);
		case IDENT: {
			final @NotNull IdentExpression ident = (IdentExpression) left;
			final LookupResultList         lrl   = ctx.lookup(ident.getText());
			if (lrl.results().size() == 0) {
				throw new ResolveError(ident, lrl);
			}
			return lrl;
		}
		default:
			throw new IllegalArgumentException();
		}

	}
}


//
//
//

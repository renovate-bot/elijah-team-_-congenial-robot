/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 2, 2005 2:28:42 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.gen.nodes;

import antlr.CommonToken;
import antlr.Token;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.Node;
import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.List;

import static tripleo.elijah.gen.TypeRef.CODE_U64;
import static tripleo.elijah.gen.TypeRef.is_integer_code;

/**
 * Please consider that there is no such thing as an ExpressionNode
 */
public class ExpressionNodeBuilder {

	@NotNull
	@Contract("_, _, _, _ -> new")
	public static IExpression binex(final TypeRef rt, final VariableReferenceImpl left, final @NotNull ExpressionOperators middle, final IExpression right) {
		// TODO Auto-generated method stub
		final ExpressionKind middle1 = Helpers.ExpressionOperatorToExpressionType(middle);
		return new BasicBinaryExpressionImpl(left, middle1, right);
	}

	@NotNull
	@Contract("_, _, _, _ -> new")
	public static IExpression binex(final TypeRef rt, final VariableReferenceImpl left, final @NotNull ExpressionOperators middle, final @NotNull TmpSSACtxNode right) { // todo wrong again
		// TODO Auto-generated method stub
		final ExpressionKind middle1 = Helpers.ExpressionOperatorToExpressionType(middle);
		return new BasicBinaryExpressionImpl(left, middle1, new StringExpressionImpl(tripleo.elijah.util.Helpers.makeToken(right.text()))); // TODO !!!
	}

	@NotNull
	public static IExpressionNode binex(final TypeRef rt, final VariableReferenceNode3 n, final ExpressionOperators opMinus, final NumericExpression integer) {
		final TypeRef typeRef = new TypeRef(null, null, "int", 80);  // TODO smells
		//
		return new MyIExpressionNode1(n, opMinus, new IntegerNode(integer, typeRef));
	}

	@NotNull
	@Contract(value = "_, _, _, _ -> new", pure = true)
	public static IExpressionNode binex(final @NotNull TypeRef rt, final VariableReferenceNode3 varref, final ExpressionOperators operators, final TmpSSACtxNode node) {
		return new IExpressionNode() {
			@Override
			public @org.jetbrains.annotations.Nullable IExpression getExpr() {
				return null;
			}

			@Override
			public boolean is_const_expr() {
				return false;
			}

			@Override
			public boolean is_underscore() {
				return false;
			}

			@Override
			public boolean is_var_ref() {
				return false;
			}

			@Override
			public boolean is_simple() {
				return false;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genText(final CompilerContext cctx) {
				return null;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genType() {
				return null; //rt.getName(); // TODO
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genText() {
				return null;
			}

			@Override
			public TypeRef getType() {
				return rt;
			}
		};
	}

	@NotNull
	public static IExpressionNode fncall(final @NotNull MethRef aMeth, final @NotNull List<LocalAgnTmpNode> of) { // TODO no so wrong anymore
		final ProcedureCallExpression pce1 = new ProcedureCallExpressionImpl();
		final Qualident               xyz  = new QualidentImpl();
		final Token                   t    = new CommonToken();
		xyz.append(tripleo.elijah.util.Helpers.string_to_ident(aMeth.getTitle()));
		pce1.identifier(xyz);
		//
		//
		final ExpressionList expl = Helpers.LocalAgnTmpNodeToListVarRef(of);
		pce1.setArgs(expl);
		//
		//
		return new IExpressionNode() {
			@Override
			public @NotNull IExpression getExpr() {
				return pce1;
			}

			@Override
			public boolean is_const_expr() {
				return false;
			}

			@Override
			public boolean is_underscore() {
				return false;
			}

			@Override
			public boolean is_var_ref() {
				return false;
			}

			@Override
			public boolean is_simple() {
				return false;
			}

			@Override
			public @NotNull String genText(final CompilerContext cctx) {
				final TypeRef       p    = aMeth.getParent();
				final int           code = p.getCode();
				final String        s    = String.format("z%d%s", code, pce1.getLeft().toString());
				final StringBuilder sb   = new StringBuilder();

				sb.append(s);
				sb.append('(');

//				final List<String> sl = new ArrayList<String>();
//				for (final IExpression arg : pce1.getArgs()) {
//					final String s2;
//					if (arg instanceof VariableReferenceImpl) {
//						s2 = ((VariableReferenceImpl) arg).getName();
//					} else {
//						s2 = (arg.toString());
//					}
//					sl.add(s2);
////					sb.append(',');
//				}
				sb.append(tripleo.elijah.util.Helpers.String_join(",", Collections2.transform(pce1.getArgs().expressions(), new Function<IExpression, String>() {
					@Nullable
					@Override
					public String apply(@Nullable IExpression input) {
						@NotNull final IExpression arg = input;
						final String               s2;
						if (arg instanceof VariableReferenceImpl) {
							s2 = ((VariableReferenceImpl) arg).getName();
						} else {
							s2 = arg.toString();
						}
						return s2;
					}
				})));
				sb.append(')');
				final String s3 = sb.toString();
				return s3;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genType() {
				NotImplementedException.raise();
				return null;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genText() {
				NotImplementedException.raise();
				return null;
			}

			@Override
			public @org.jetbrains.annotations.Nullable TypeRef getType() {
				NotImplementedException.raise();
				return null;
			}
		};
	}

	public static @NotNull IExpressionNode fncall(final String string, final @NotNull List<LocalAgnTmpNode> of) { // todo wrong
		// TODO Auto-generated method stub
		final ProcedureCallExpression pce1 = new ProcedureCallExpressionImpl();
		final Qualident               xyz  = new QualidentImpl();
//		final Token t = tripleo.elijah.util.Helpers.makeToken(string);
		xyz.append(tripleo.elijah.util.Helpers.string_to_ident(string));
		pce1.identifier(xyz);
		//
		final ExpressionList expl = Helpers.LocalAgnTmpNodeToListVarRef(of);
		pce1.setArgs(expl);
		//
		return new IExpressionNode() {
			@Override
			public @NotNull IExpression getExpr() {
				return pce1;
			}

			@Override
			public boolean is_const_expr() {
				return false;
			}

			@Override
			public boolean is_underscore() {
				return false;
			}

			@Override
			public boolean is_var_ref() {
				return false;
			}

			@Override
			public boolean is_simple() {
				return false;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genText(final CompilerContext cctx) {
				NotImplementedException.raise();
				return null;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genType() {
				NotImplementedException.raise();
				return null;
			}

			@Override
			public @org.jetbrains.annotations.Nullable String genText() {
				NotImplementedException.raise();
				return null;
			}

			@Override
			public @org.jetbrains.annotations.Nullable TypeRef getType() {
				NotImplementedException.raise();
				return null;
			}
		};
	}

//	public static IExpressionNode fncall(String string, List<@NotNull NumericExpression> list_of) {
//		final ExpressionList expl = Helpers.LocalAgnTmpNodeToListVarRef(of);
//		return fncall(string, expl);
//	}

	/**
	 * Return a parser-level OS_ELement for std integer {@param i}
	 *
	 * @param string string in question
	 * @return OS_Ident
	 */
	@NotNull
	@Contract("_ -> new")
	public static IdentExpression ident(final String string) {
		// TODO Parser level elements should not be used here
		return new IdentExpressionImpl(tripleo.elijah.util.Helpers.makeToken(string), "<inline-absent>");

	}

	/**
	 * Return a parser-level OS_ELement for std integer {@param i}
	 *
	 * @param i integer in question
	 * @return OS_Integer
	 */
	@NotNull
	@Contract("_ -> new")
	public static NumericExpression integer(final int i) {
		// TODO Parser level elements should not be used here
		return new NumericExpressionImpl(i);
	}

	@NotNull
	@Contract("_, _, _ -> new")
	public static VariableReferenceNode3 varref(final String string, final Node container, final TypeRef typeRef) {
		return new VariableReferenceNode3(string, container, typeRef);
	}

	private static class MyIExpressionNode1 implements IExpressionNode {
		private final VariableReferenceNode3 _left;
		private final ExpressionOperators    _middle;
		private final IExpressionNode _right;

		public MyIExpressionNode1(final VariableReferenceNode3 left, final ExpressionOperators middle, final IExpressionNode right) {
			_left   = left;
			_middle = middle;
			_right  = right;
		}

		static @NotNull String printableExpression(@NotNull final IExpression expression) {
//			if (expression instanceof OS_Integer) {
//				return Integer.toString(((OS_Integer) expression).getValue());
//			} else
			if (expression instanceof NumericExpression) {
				return Integer.toString(((NumericExpression) expression).getValue());
			}
			return "-------------7";
		}

		@Override
		public boolean is_const_expr() {
			return false;
		}

		@Override
		public boolean is_underscore() {
			return false;
		}

		@Override
		public boolean is_var_ref() {
			return false;
		}

		@Override
		public boolean is_simple() {
			return false;
		}

		@Override
		public String genText(final CompilerContext cctx) {
			final String left    = _left.genText();
			final String middle1 = _middle.getSymbol();
			final String right   = printableExpression(_right.getExpr());

			return String.format("%s %s %s", left, middle1, right);
		}

		@Override
		public @org.jetbrains.annotations.Nullable IExpression getExpr() {
			return null;
		}

		@Override
		public @org.jetbrains.annotations.Nullable String genType() {
			// TODO need lookup somewhere, prolly not here tho...
			if (_middle == ExpressionOperators.OP_MINUS) {
				if (_left.getType().getCode() == CODE_U64 &&
						is_integer_code(_right.getType().getCode())) {
					return _left.getType().genName();
				}
			}
			return null;
		}

		@Override
		public @org.jetbrains.annotations.Nullable String genText() {
			return null;
		}

		@Override
		public @org.jetbrains.annotations.Nullable TypeRef getType() {
			return null;
		}
	}

}

//
//
//

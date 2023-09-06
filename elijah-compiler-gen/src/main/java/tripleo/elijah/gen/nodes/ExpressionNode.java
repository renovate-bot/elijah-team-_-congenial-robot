/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import org.eclipse.jdt.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

/**
 * @author Tripleo(acer)
 */
public class ExpressionNode implements IExpressionNode {

	public @Nullable String genName;  // TODO since when does expression have a name?
	public @Nullable String genText;
	public @Nullable String genType;

	public           boolean    _is_const_expr;
	public @Nullable OS_Element ref_;

	private @Nullable IExpression iex;

	/**
	 * For {@link VariableReferenceNode2}
	 */
	public ExpressionNode() {
		genName        = null;
		genText        = null;
		genType        = null;
		_is_const_expr = false;
		ref_           = null;
		iex            = null;
	}
	/*
	public ExpressionNode(@NonNull  OS_Integer expr1) {
		// TODO should  be interface
		genName=((Integer)expr1.getValue()).toString(); // TODO likely wrong
		genText=expr1.toString(); // TODO likely wrong
		_is_const_expr = true;
		iex = expr1;
	}
	*/

	public ExpressionNode(@NonNull final IExpression expr1) {
		// TODO Auto-generated constructor stub
		if (expr1 != null) {
			genName        = expr1.toString(); // TODO likely wrong
			genText        = expr1.toString(); // TODO likely wrong
			_is_const_expr = expr1.getLeft() instanceof StringExpression
					|| expr1.getLeft() instanceof NumericExpression; // TODO more
			iex            = expr1;
		}
	}

	@Override
	public IExpression getExpr() {
		return iex;
	}

	@Override
	public boolean is_const_expr() {
		return _is_const_expr;
	}

	@Override
	public boolean is_underscore() {
		// TODO Auto-generated method stub
		if (iex != null && iex instanceof VariableReferenceImpl) {
			return ((VariableReferenceImpl) iex).getName().equals("_");
		}
		return false;
	}

	@Override
	public boolean is_var_ref() {
		return (iex != null && iex instanceof VariableReferenceImpl);
	}

	@Override
	public boolean is_simple() {
		if (iex != null && iex instanceof VariableReferenceImpl) {
			return iex.is_simple();
		}
		return is_const_expr() || is_underscore();
	}

	@Override
	public String genText(final CompilerContext cctx) {
//		if (iex instanceof OS_Integer) {
//			final int value = ((OS_Integer) iex).getValue();
//			return ((Integer) value).toString();
//		}
		if (iex instanceof NumericExpression) {
			final int value = ((NumericExpression) iex).getValue();
			return ((Integer) value).toString();
		}
		if (iex instanceof BasicBinaryExpression) {
			if (iex.getLeft() instanceof VariableReferenceImpl) {

				final String left_side  = ((VariableReferenceImpl) this.iex.getLeft()).getName();
				String       right_side = null;

				final BasicBinaryExpression abe = (BasicBinaryExpression) this.iex;
//				if (abe.getRight() instanceof OS_Integer) {
//					right_side = ""+((OS_Integer) abe.getRight()).getValue();
//				}
				if (abe.getRight() instanceof NumericExpression) {
					right_side = "" + ((NumericExpression) abe.getRight()).getValue();
				}
				if (abe.getKind() == ExpressionKind.SUBTRACTION) {
					final String s = String.format("%s - %s", left_side, right_side);
					return s;
				}
				if (abe.getKind() == ExpressionKind.MULTIPLY) {
					final String s = String.format("%s * %s", left_side, right_side);
					return s;
				}

				return "---------------2";
			}
		}
//		if (iex instanceof OS_Integer) {
//			final int value = ((OS_Integer) iex).getValue();
//			return ((Integer) value).toString();
//		}
		if (iex instanceof NumericExpression) {
			final int value = ((NumericExpression) iex).getValue();
			return ((Integer) value).toString();
		}
		if (iex instanceof ProcedureCallExpression) {
			return getStringPCE((ProcedureCallExpression) iex);
		}
//		if (iex instanceof VariableReferenceImpl) {
		NotImplementedException.raise();
		return "vai"; // TODO hardcoded
	}

	static @NotNull String getStringPCE(final @NotNull ProcedureCallExpression expr) {
		final int code = 1000; // TODO hardcoded
		return Helpers.getFunctionName(code, expr.getLeft().toString(), expr.exprList());
	}

	@Override
	public String genText() {
		return genText(null);
	}

	@Override
	public @NotNull String genType() {
		return "u64";  // TODO harcoded
	}

	@Override
	public @Nullable TypeRef getType() {
		return null;
	}
}

//
//
//

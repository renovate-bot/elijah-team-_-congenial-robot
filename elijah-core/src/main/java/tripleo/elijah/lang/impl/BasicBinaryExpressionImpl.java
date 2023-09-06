/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */

package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;

public class BasicBinaryExpressionImpl implements BasicBinaryExpression {

	public @Nullable ExpressionKind _kind;
	OS_Type _type;
	public @Nullable IExpression    left;
	public @Nullable IExpression    right;
	private          ExpressionList args;

	public BasicBinaryExpressionImpl() {
		left  = null;
		right = null;
		_kind = null;
	}

	public BasicBinaryExpressionImpl(final IExpression aLeft, final ExpressionKind aType, final IExpression aRight) {
		left  = aLeft;
		_kind = aType;
		right = aRight;
	}

	public @NotNull ExpressionList getArgs() {
		return this.args;
	}

	public void setArgs(ExpressionList ael) {
		this.args = ael;
	}

	@Override
	public ExpressionKind getKind() {
		return _kind;
	}

	@Override
	public IExpression getLeft() {
		return left;
	}

	@Override
	public OS_Type getType() {
		return _type;
	}

	@Override
	public boolean is_simple() {
		return getLeft().is_simple() && getRight().is_simple();
	}

	@Override
	public IExpression getRight() {
		return right;
	}

	@Override
	public void set(final @NotNull IBinaryExpression aEx) {
		left  = aEx.getLeft();
		_kind = aEx.getKind();
		right = aEx.getRight();
	}

	@Override
	public void setRight(final IExpression aRight) {
		right = aRight;
	}

	@Override
	public String repr_() {
		return String.format("<Expression %s %s %s>", left, _kind, right);
	}

	@Override
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

	@Override
	public void shift(final ExpressionKind aType) {
		left  = new BasicBinaryExpressionImpl(left, _kind, right); // TODO
		_kind = aType;
		right = null;
	}

	@Override
	public void setLeft(final IExpression aLeft) {
		left = aLeft;
	}

	@Override
	public void setKind(final ExpressionKind aKind) {
		_kind = aKind;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder         sb  = new StringBuilder();
		final BasicBinaryExpression abe = this;
		switch (abe.getKind()) {
		case ASSIGNMENT:
			sb.append(abe.getLeft().toString());
			sb.append("=");
			sb.append(abe.getRight().toString());
			break;
		case EQUAL:
			sb.append(abe.getLeft().toString());
			sb.append("==");
			sb.append(abe.getRight().toString());
			break;
		case NOT_EQUAL:
			sb.append(abe.getLeft().toString());
			sb.append("!=");
			sb.append(abe.getRight().toString());
			break;
		case ADDITION:
			sb.append(abe.getLeft().toString());
			sb.append("+");
			sb.append(abe.getRight().toString());
			break;
		case SUBTRACTION:
			sb.append(abe.getLeft().toString());
			sb.append("-");
			sb.append(abe.getRight().toString());
			break;
		case MULTIPLY:
			sb.append(abe.getLeft().toString());
			sb.append("*");
			sb.append(abe.getRight().toString());
			break;
		case DIVIDE:
			sb.append(abe.getLeft().toString());
			sb.append("/");
			sb.append(abe.getRight().toString());
			break;
		case AUG_PLUS:
			sb.append(abe.getLeft().toString());
			sb.append("+=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_MINUS:
			sb.append(abe.getLeft().toString());
			sb.append("-=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_MULT:
			sb.append(abe.getLeft().toString());
			sb.append("*=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_DIV:
			sb.append(abe.getLeft().toString());
			sb.append("/=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_MOD:
			sb.append(abe.getLeft().toString());
			sb.append("%=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_SR:
			sb.append(abe.getLeft().toString());
			sb.append(">>=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_BSR:
			sb.append(abe.getLeft().toString());
			sb.append(">>>=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_SL:
			sb.append(abe.getLeft().toString());
			sb.append("<<=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_BAND:
			sb.append(abe.getLeft().toString());
			sb.append("&=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_BXOR:
			sb.append(abe.getLeft().toString());
			sb.append("^=");
			sb.append(abe.getRight().toString());
			break;
		case AUG_BOR:
			sb.append(abe.getLeft().toString());
			sb.append("|=");
			sb.append(abe.getRight().toString());
			break;
		case IS_A:
			sb.append(abe.getLeft().toString());
			sb.append(" is_a ");
			sb.append(abe.getRight().toString());
			break;
		case BNOT:
			sb.append(abe.getLeft().toString());
			sb.append("~");
			sb.append(abe.getRight().toString());
			break;
		case LNOT:
			sb.append("!");
			sb.append(abe.getLeft().toString());
			sb.append(abe.getRight().toString());
			break;
		case MODULO:
			sb.append(abe.getLeft().toString());
			sb.append("%");
			sb.append(abe.getRight().toString());
			break;
		case BAND:
			sb.append(abe.getLeft().toString());
			sb.append(" & ");
			sb.append(abe.getRight().toString());
			break;
		case BOR:
			sb.append(abe.getLeft().toString());
			sb.append(" | ");
			sb.append(abe.getRight().toString());
			break;
		case BXOR:
			sb.append(abe.getLeft().toString());
			sb.append(" ^= ");
			sb.append(abe.getRight().toString());
			break;
		case LT_:
			sb.append(abe.getLeft().toString());
			sb.append(" < ");
			sb.append(abe.getRight().toString());
			break;
		case GT:
			sb.append(abe.getLeft().toString());
			sb.append(" > ");
			sb.append(abe.getRight().toString());
			break;
		case LE:
			sb.append(abe.getLeft().toString());
			sb.append(" <= ");
			sb.append(abe.getRight().toString());
			break;
		case GE:
			sb.append(abe.getLeft().toString());
			sb.append(" >= ");
			sb.append(abe.getRight().toString());
			break;
//			case INC:
//				sb.append(abe.getLeft().toString());
//				sb.append(abe.getRight().toString());
//				sb.append("++");
//				break;
//			case DEC:
//				sb.append(abe.getLeft().toString());
//				sb.append(abe.getRight().toString());
//				sb.append("--");
//				break;
		case LAND:
			sb.append(abe.getLeft().toString());
			sb.append(" & ");
			sb.append(abe.getRight().toString());
			break;
		case LOR:
			sb.append(abe.getLeft().toString());
			sb.append(" | ");
			sb.append(abe.getRight().toString());
			break;
		case BSHIFTR:
			sb.append(abe.getLeft().toString());
			sb.append(" >>>= ");
			sb.append(abe.getRight().toString());
			break;
		case LSHIFT:
			sb.append(abe.getLeft().toString());
			sb.append(" << ");
			sb.append(abe.getRight().toString());
			break;
		case RSHIFT:
			sb.append(abe.getLeft().toString());
			sb.append(" >> ");
			sb.append(abe.getRight().toString());
			break;
		case DOT_EXP:
			sb.append(abe.getLeft().toString());
			sb.append(" DOT_EXP ");
			sb.append(abe.getRight().toString());
			break;
//			case INDEX_OF:
//				sb.append(abe.getLeft().toString());
//				sb.append(" INDEX_OF ");
//				sb.append(abe.getRight().toString());
//				break;
		case GET_ITEM:
			sb.append(abe.getLeft().toString());
			sb.append(" GET_ITEM ");
			sb.append(abe.getRight().toString());
			break;
		case SET_ITEM:
			sb.append(abe.getLeft().toString());
			sb.append(" SET_ITEM ");
			sb.append(abe.getRight().toString());
			break;

		//
		// SHOULD PROBABLY THROW
		//

		case FUNC_EXPR:
			sb.append(abe.getLeft().toString());
			sb.append(" FUNC_EXPR ");
			sb.append(abe.getRight().toString());
			break;
		case TO_EXPR:
			sb.append(abe.getLeft().toString());
			sb.append(" TO_EXPR ");
			sb.append(abe.getRight().toString());
			break;

		case QIDENT:
			throw new IllegalStateException();
		case STRING_LITERAL:
			throw new IllegalStateException();
		case PROCEDURE_CALL:
			throw new IllegalStateException();
		case VARREF:
			throw new IllegalStateException();
		case IDENT:
			throw new IllegalStateException();
		case NUMERIC:
			throw new IllegalStateException();
		case FLOAT:
			throw new IllegalStateException();

			//
			// SINGLE EXPRESSIONS
			//

		case INCREMENT:
			sb.append("++");
			sb.append(abe.getLeft().toString());
//				sb.append(abe.getRight().toString());
			break;
		case DECREMENT:
			sb.append("--");
			sb.append(abe.getLeft().toString());
//				sb.append(abe.getRight().toString());
			break;
		case NEG:
			sb.append("-");
			sb.append(abe.getLeft().toString());
			sb.append(abe.getRight().toString()); // TODO ??
			break;
		case POS:
			sb.append("+");
			sb.append(abe.getLeft().toString());
			sb.append(abe.getRight().toString());
			break;
		case POST_INCREMENT:
			sb.append(abe.getLeft().toString());
//				sb.append(abe.getRight().toString());
			sb.append("++");
			break;
		case POST_DECREMENT:
			sb.append(abe.getLeft().toString());
			sb.append("--");
//				sb.append(abe.getRight().toString());
			break;

		}
		return sb.toString();
	}

}

//
//
//

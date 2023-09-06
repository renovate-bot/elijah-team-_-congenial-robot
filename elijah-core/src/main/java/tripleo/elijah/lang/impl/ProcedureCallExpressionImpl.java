/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;

// TODO is ExpressionList an IExpression?
public class ProcedureCallExpressionImpl implements tripleo.elijah.lang.i.ProcedureCallExpression {

//	public ProcedureCallExpression(final Token aToken, final ExpressionList aExpressionList, final Token aToken1) {
//		throw new NotImplementedException();
//	}

	private IExpression _left;

	OS_Type _type;
	// region right-side
	private ExpressionList args = new ExpressionListImpl();

	/**
	 * Make sure you call {@link #identifier} or {@link #setLeft(IExpression)}
	 * and {@link #setArgs(ExpressionList)}
	 */
	public ProcedureCallExpressionImpl() {
	}

	/**
	 * Get the argument list
	 *
	 * @return the argument list
	 */
	@Override
	public ExpressionList exprList() {
		return args;
	}

	// endregion

//	@Override
//	public void visitGen(ICodeGen visit) {
//		// TODO Auto-generated method stub
//		NotImplementedException.raise();
//	}

	// region kind

//	@Override
//	public @NotNull List<FormalArgListItem> getArgs() {
//		return args;
//	}

	@Override
	public ExpressionList getArgs() {
		return args;
	}

	// endregion

	// region left-side

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.PROCEDURE_CALL;
	}

	@Override
	public IExpression getLeft() {
		return _left;
	}

	@Override
	public OS_Type getType() {
		return _type;
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name might come as DotExpression or IdentExpression
	 */
	@Override
	public void identifier(final IExpression xyz) {
		setLeft(xyz);
	}

	@Override
	public String printableString() {
		return String.format("%s%s", getLeft(), args != null ? args.toString() : "()");
	}

	// endregion

	@Override
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct?
	}

	/*
	 * public OS_Element getParent() { return null; }
	 */

	// region representation

	/**
	 * change then argument list all at once
	 *
	 * @param ael the new value
	 */
	@Override
	public void setArgs(final ExpressionList ael) {
		args = ael;
	}

	public @NotNull String getReturnTypeString() {
		return "int"; // TODO hardcoded
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name in Qualident form (might come as DotExpression in
	 *            future)
	 */
	public void identifier(final Qualident xyz) {
		setLeft(xyz);
	}

	// endregion

	// region type (to remove)

	@Override
	public String repr_() {
		return toString();
	}

	@Override
	public void setKind(final ExpressionKind aIncrement) {
		throw new IllegalArgumentException();
	}

	/**
	 * @see #identifier(Qualident)
	 */
	@Override
	public void setLeft(final IExpression iexpression) {
		_left = iexpression;
	}

	@Override
	public String toString() {
		return String.format("ProcedureCallExpression{%s %s}", getLeft(), args != null ? args.toString() : "()");
	}

	// endregion

}

//
//
//

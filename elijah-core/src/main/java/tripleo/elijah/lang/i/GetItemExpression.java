package tripleo.elijah.lang.i;

import antlr.Token;

public interface GetItemExpression extends IExpression {
	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#getKind()
	 */
	@Override
	ExpressionKind getKind();

	@Override
	OS_Type getType();

	IExpression index();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#is_simple()
	 */
	@Override
	boolean is_simple();

	void parens(Token lb, Token rb);

	@Override
	void setType(OS_Type deducedExpression);
}

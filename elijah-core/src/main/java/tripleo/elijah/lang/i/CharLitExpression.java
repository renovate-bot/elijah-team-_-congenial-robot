package tripleo.elijah.lang.i;

public interface CharLitExpression extends IExpression {
	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#getType()
	 */
	@Override
	ExpressionKind getKind();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#getLeft()
	 */
	@Override
	IExpression getLeft();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#repr_()
	 */
	@Override
	String repr_();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#set(tripleo.elijah.lang.impl.
	 * ExpressionType)
	 */
	@Override
	void setKind(ExpressionKind aIncrement);

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#setLeft(tripleo.elijah.lang.impl.
	 * IExpression)
	 */
	@Override
	void setLeft(IExpression iexpression);

	@Override
	void setType(OS_Type deducedExpression);

	@Override
	String toString();
}

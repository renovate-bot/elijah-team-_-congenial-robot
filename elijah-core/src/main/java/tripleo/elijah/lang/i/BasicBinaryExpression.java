package tripleo.elijah.lang.i;

public interface BasicBinaryExpression extends IBinaryExpression {

	@Override
	void setKind(ExpressionKind aKind);

	@Override
	void setLeft(IExpression aLeft);

	@Override
	void setType(OS_Type deducedExpression);

	void shift(ExpressionKind aType);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();
}

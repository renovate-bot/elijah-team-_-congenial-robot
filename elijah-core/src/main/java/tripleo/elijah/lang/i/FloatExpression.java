package tripleo.elijah.lang.i;

public interface FloatExpression extends IExpression {
	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aType);

	@Override
	void setLeft(IExpression aLeft);

	@Override
	void setType(OS_Type deducedExpression);

	@Override
	String toString();
}

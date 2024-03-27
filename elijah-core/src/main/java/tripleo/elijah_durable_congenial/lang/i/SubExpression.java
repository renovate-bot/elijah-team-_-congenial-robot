package tripleo.elijah_durable_congenial.lang.i;

public interface SubExpression extends IExpression {
	IExpression getExpression();

	@Override
	ExpressionKind getKind();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	void setType(OS_Type deducedExpression);
}

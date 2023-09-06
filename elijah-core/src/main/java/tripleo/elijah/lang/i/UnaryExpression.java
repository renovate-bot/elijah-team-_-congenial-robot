package tripleo.elijah.lang.i;

public interface UnaryExpression extends IExpression {
	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	void setType(OS_Type deducedExpression);
}

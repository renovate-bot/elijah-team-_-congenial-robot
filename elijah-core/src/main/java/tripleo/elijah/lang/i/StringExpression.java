package tripleo.elijah.lang.i;

public interface StringExpression extends IExpression {
	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	String getText();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	void set(String g);

	@Override
	void setLeft(IExpression iexpression);

	@Override
	void setType(OS_Type deducedExpression);

	@Override
	String toString();
}

package tripleo.elijah.lang.i;

public interface TypeCastExpression extends IExpression {
	@Override
	OS_Type getType();

	TypeName getTypeName();

	@Override
	boolean is_simple();

	@Override
	void setType(OS_Type deducedExpression);

	void setTypeName(TypeName typeName);

	TypeName typeName();
}

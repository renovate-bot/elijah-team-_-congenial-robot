package tripleo.elijah.lang.i;

public interface FunctionHeader {
	FormalArgList getFal();

	FunctionModifiers getModifier();

	IdentExpression getName();

	TypeName getReturnType();

	void setFal(FormalArgList aFal);

	void setModifier(FunctionModifiers aModifiers);

	void setName(IdentExpression aIdentExpression);

	void setReturnType(TypeName aTypeName);
}

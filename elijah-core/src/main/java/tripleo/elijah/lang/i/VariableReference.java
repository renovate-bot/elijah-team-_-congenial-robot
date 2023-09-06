package tripleo.elijah.lang.i;

import antlr.Token;

public interface VariableReference extends IExpression {
	String getName();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	void setMain(String s);

	void setMain(Token t);

	@Override
	void setType(OS_Type deducedExpression);

	@Override
	String toString();
}

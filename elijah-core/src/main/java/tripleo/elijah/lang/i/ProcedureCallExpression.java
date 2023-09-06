package tripleo.elijah.lang.i;

public interface ProcedureCallExpression extends IExpression {
	ExpressionList exprList();

	ExpressionList getArgs();

	@Override
	OS_Type getType();

	void identifier(IExpression ee);

	String printableString();

	void setArgs(ExpressionList aExpl);

	@Override
	String toString();
}

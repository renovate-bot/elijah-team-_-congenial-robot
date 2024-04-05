package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

public interface ConstructStatement extends FunctionItem, StatementItem, OS_Element {
	ExpressionList getArgs();

	@Override
	Context getContext();

	IExpression getExpr();

	@Override
	OS_Element getParent();

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

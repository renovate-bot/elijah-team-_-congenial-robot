package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.ElElementVisitor;

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

package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

public interface YieldExpression extends IExpression, OS_Element {
	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

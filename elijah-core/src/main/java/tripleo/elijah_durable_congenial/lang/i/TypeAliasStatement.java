package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

public interface TypeAliasStatement extends OS_Element {
	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	void make(IdentExpression x, Qualident y);

	void setBecomes(Qualident qq);

	void setIdent(IdentExpression aToken);

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

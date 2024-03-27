package tripleo.elijah_durable_congenial.lang.i;

import antlr.Token;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

public interface AccessNotation extends OS_Element {
	Token getCategory();

	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	void setCategory(Token category);

	void setShortHand(Token shorthand);

	void setTypeNames(TypeNameList tnl);

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

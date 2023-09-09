package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.ElElementVisitor;

public interface FormalArgListItem extends OS_Element, OS_NamedElement, ClassItem {
	@Override
		// OS_Element
	Context getContext();

	IdentExpression getNameToken();

	@Override
		// OS_Element
	OS_Element getParent();

	@Override
		// OS_Element2
	OS_ElementName name();

	void setName(IdentExpression s);

	void setTypeName(TypeName tn1);

	TypeName typeName();

	@Override
		// OS_Element
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

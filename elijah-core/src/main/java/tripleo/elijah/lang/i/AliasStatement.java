package tripleo.elijah.lang.i;

public interface AliasStatement extends OS_Element, ModuleItem, ClassItem, FunctionItem, OS_NamedElement {
	Qualident getExpression();

	void setExpression(Qualident aXy);

	void setName(IdentExpression aI1);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

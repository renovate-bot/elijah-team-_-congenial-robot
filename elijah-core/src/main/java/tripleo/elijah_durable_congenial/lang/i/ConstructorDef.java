package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

public interface ConstructorDef extends FunctionDef {
	@Override
	OS_Element getParent();

	@Override
    OS_ElementName name();

	@Override
	void postConstruct();

	@Override
	void setFal(FormalArgList aFal);

	@Override
	void setHeader(FunctionHeader aFunctionHeader);

	@Override
	String toString();

	@Override
	void visitGen(ElElementVisitor visit); // OS_Element

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

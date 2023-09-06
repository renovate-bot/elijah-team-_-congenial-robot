package tripleo.elijah.lang.i;

public interface DestructorDef extends OS_Element {
	void postConstruct();

	void scope(Scope3 aSco);

	void setFal(FormalArgList aFal);

	void setHeader(FunctionHeader aFunctionHeader);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

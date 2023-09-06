package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.BuiltInTypes;

public interface OS_Type {
	enum Type {
		ANY, BUILT_IN, FUNC_EXPR, FUNCTION, GENERIC_TYPENAME, UNIT_TYPE, UNKNOWN, USER, USER_CLASS
	}

	static boolean isConcreteType(final OS_Element element) {
		return element instanceof ClassStatement;
		// enum
		// type
	}

	/* @ requires type_of_type = Type.BUILT_IN; */
	BuiltInTypes getBType();

	ClassStatement getClassOf();

	OS_Element getElement();

	Type getType();

	/* @ requires type_of_type = Type.USER; */
	TypeName getTypeName();

	boolean isEqual(OS_Type aType);

	boolean isUnitType();

	OS_Type resolve(Context ctx);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();
}

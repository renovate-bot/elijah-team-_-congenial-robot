package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface VariableTypeName extends TypeName {
	void addGenericPart(TypeNameList tn2);

	@Override
	boolean equals(Object o);

	@Override
	Context getContext();

	TypeNameList getGenericPart();

	@NotNull
	Collection<TypeModifiers> getModifiers();

	Qualident getRealName();

	OS_Element getResolvedElement();

	@Override
	int hashCode();

	boolean hasResolvedElement();

	@Override
	void setContext(Context ctx);

	@Override
	boolean isNull();

	@Override
	Type kindOfType();

	void setName(Qualident aQualident);

	void setResolvedElement(OS_Element element);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	/* #@ requires pr_name != null; */
	// pr_name is null when first created
	@Override
	String toString();
}

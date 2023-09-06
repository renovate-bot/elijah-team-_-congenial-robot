package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.impl.RegularTypeNameImpl;
import tripleo.elijah.util.Helpers;

public interface RegularTypeName extends NormalTypeName {
	/*
	 * Null context. Possibly only for testing.
	 */
	static @NotNull RegularTypeName makeWithStringTypeName(@NotNull String aTypeName) {
		final RegularTypeName R = new RegularTypeNameImpl(null);
		R.setName(Helpers.string_to_qualident(aTypeName));
		return R;
	}

	void addGenericPart(TypeNameList tn2);

	Context getContext();

	TypeNameList getGenericPart();

	String getName();

	Qualident getRealName();

	@Override
	OS_Element getResolvedElement();

	@Override
	boolean hasResolvedElement();

	Type kindOfType();

	void setContext(Context ctx);

	void setName(Qualident aS);

	@Override
	void setResolvedElement(OS_Element element);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();
}

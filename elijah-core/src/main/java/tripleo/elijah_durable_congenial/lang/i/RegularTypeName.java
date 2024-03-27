package tripleo.elijah_durable_congenial.lang.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.impl.RegularTypeNameImpl;
import tripleo.elijah_durable_congenial.util.Helpers;

public interface RegularTypeName extends NormalTypeName {
	/*
	 * Null context. Possibly only for testing.
	 */
	static @NotNull RegularTypeName makeWithStringTypeName(@NotNull String aTypeName) {
		final RegularTypeName R = new RegularTypeNameImpl(null);
		R.setName(Helpers.string_to_qualident(aTypeName));
		return R;
	}

	@Override
	void addGenericPart(TypeNameList tn2);

	@Override
	TypeNameList getGenericPart();

	@Override
	String getName();

	@Override
	Qualident getRealName();

	@Override
	void setName(Qualident aS);

	@Override
	OS_Element getResolvedElement();

	@Override
	boolean hasResolvedElement();

	@Override
	Context getContext();

	@Override
	Type kindOfType();

	@Override
	void setContext(Context ctx);

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

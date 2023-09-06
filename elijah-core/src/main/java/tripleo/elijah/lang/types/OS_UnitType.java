package tripleo.elijah.lang.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Type;


public class OS_UnitType extends __Abstract_OS_Type {

	@Override
	public @NotNull String asString() {
		return "<OS_UnitType>";
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.UNIT_TYPE;
	}

	@Override
	public @Nullable OS_Element getElement() {
		return null;
	}

	@Override
	public @NotNull Type getType() {
		return Type.UNIT_TYPE;
	}

	@Override
	public boolean isUnitType() {
		return true;
	}

	@Override
	public @NotNull String toString() {
		return "<UnitType>";
	}
}



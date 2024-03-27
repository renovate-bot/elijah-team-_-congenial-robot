package tripleo.elijah_durable_congenial.world.impl;

import tripleo.elijah_durable_congenial.lang.i.OS_Package;
import tripleo.elijah_durable_congenial.world.i.LivingPackage;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;

public class DefaultLivingPackage implements LivingPackage {
	private final OS_Package _element;

	public DefaultLivingPackage(final OS_Package aElement) {
		_element = aElement;
	}

	@Override
	public int getCode() {
		return 0;
	}

	@Override
	public OS_Package getElement() {
		return _element;
	}
}

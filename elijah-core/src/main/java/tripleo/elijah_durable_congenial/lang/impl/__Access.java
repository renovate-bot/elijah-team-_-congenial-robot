package tripleo.elijah_durable_congenial.lang.impl;

import tripleo.elijah_durable_congenial.lang.i.AccessNotation;
import tripleo.elijah_durable_congenial.lang.i.ClassItem;
import tripleo.elijah_durable_congenial.lang.i.SmallWriter;
import tripleo.elijah_durable_congenial.lang.i.AccessNotation;
import tripleo.elijah_durable_congenial.lang.i.ClassItem;
import tripleo.elijah_durable_congenial.lang.i.SmallWriter;

public abstract class __Access implements ClassItem {
	private AccessNotation _an;

	@Override
	public AccessNotation getAccess() {
		return _an;
	}

	@Override
	public void setAccess(AccessNotation an) {
		_an = an;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}
}

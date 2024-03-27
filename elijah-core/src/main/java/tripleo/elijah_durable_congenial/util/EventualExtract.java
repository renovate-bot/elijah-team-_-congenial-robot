package tripleo.elijah_durable_congenial.util;

import tripleo.elijah.Eventual;

public final class EventualExtract {
	private EventualExtract() {
	}

	public static <T> T of(final Eventual<T> aEventual) {
		final Object[] t = new Object[1];
		aEventual.then(t0 -> t[0] = t0);
		//noinspection unchecked
		return (T) t[0];
	}
}

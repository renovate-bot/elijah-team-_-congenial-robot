package tripleo.elijah.util;

import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Diagnostic;

public class Maybe<T> {
	public final @Nullable Diagnostic exc;
	public final @Nullable T          o;

	public Maybe(final @Nullable T o, final Diagnostic exc) {
		if (o == null) {
			if (exc == null) {
				throw new IllegalStateException("Both o and exc are null!");
			}
		} else {
			if (exc != null) {
				throw new IllegalStateException("Both o and exc are null (2)!");
			}
		}

		this.o   = o;
		this.exc = exc;
	}

	public boolean isException() {
		return exc != null;
	}

	//@Override

	@Override
	public String toString() {
		int st=0;
		if (o == null && exc == null) { st=1; }
		if (o != null && exc == null) { st=2; }
		if (o == null && exc != null) { st=3; }
		switch (st) {
		case 1, 2, 3 -> {
		}
		default -> {assert false;}
		}

		var existential = st;
		return MoreObjects.toStringHelper(this)
				.add("!E", existential)
				.add("exc", exc)
				.add("o", o)
				.toString();
	}
}

package tripleo.elijah;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.Promise;

public class PromiseReadySupplier<T> implements ReadySupplier<T> {
	private final Promise<T, Void, Void> p;

	public PromiseReadySupplier(final Promise<T, Void, Void> aP) {
		p = aP;
	}

	@Override
	public T get() {
		return null;
	}

	@Override
	public boolean ready() {
		return p.isResolved();
	}

	public void then(final DoneCallback<T> aO) {
		p.then(aO);
	}
}

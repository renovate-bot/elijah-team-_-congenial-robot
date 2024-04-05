package tripleo.elijah.util;

import java.util.function.Supplier;

public class LazySupplier<T> implements Supplier<T> {
	private final Supplier<T> supplier;
	private boolean lazy_called;
	private T lazy;

	public LazySupplier(final Supplier<T> aSupplier) {
		supplier = aSupplier;
	}

	@Override
	public T get() {
		if (!lazy_called) {
			lazy = supplier.get();
			lazy_called = true;
		}
		return lazy;
	}
}

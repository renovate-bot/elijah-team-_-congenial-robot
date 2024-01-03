package tripleo.elijah.util;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.diagnostic.Diagnostic;

/**
 * An emulation of Rust's Result type
 *
 * @param <T> the success type
 */
public class Operation2<T> {
	private final Mode mode;
	private final T    succ;

	private final Diagnostic exc;

	public static <T> @NotNull Operation2<T> failure(final Diagnostic aException) {
		final Operation2<T> op = new Operation2<>(null, aException, Mode.FAILURE);
		return op;
	}

	public static <T> @NotNull Operation2<T> success(final T aSuccess) {
		final Operation2<T> op = new Operation2<>(aSuccess, null, Mode.SUCCESS);
		return op;
	}

	public Operation2(final T aSuccess, final Diagnostic aException, final Mode aMode) {
		succ = aSuccess;
		exc  = aException;
		mode = aMode;

		if (succ == exc)
			throw new AssertionError();
	}

	public Diagnostic failure() {
		return exc;
	}

	public Mode mode() {
		return mode;
	}

	public T success() {
		return succ;
	}
}

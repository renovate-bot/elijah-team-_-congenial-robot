package tripleo.elijah.util;

import org.jetbrains.annotations.NotNull;

import static tripleo.elijah.util.Mode.FAILURE;
import static tripleo.elijah.util.Mode.SUCCESS;

/**
 * An emulation of Rust's Result type
 *
 * @param <T> the success type
 */
public class Operation<T> {
	private final Mode mode;
	private final T    succ;

	private final Exception exc;

	public static <T> @NotNull Operation<T> failure(final Exception aException) {
		final Operation<T> op = new Operation<>(null, aException, FAILURE);
		return op;
	}

	public static <T> @NotNull Operation<T> success(final T aSuccess) {
		final Operation<T> op = new Operation<>(aSuccess, null, SUCCESS);
		return op;
	}

	public Operation(final T aSuccess, final Exception aException, final Mode aMode) {
		succ = aSuccess;
		exc  = aException;
		mode = aMode;

		assert succ != exc;
	}

	public static <T> @NotNull Operation<T> failure_simple(final String aS) {
		Operation<T> R = new Operation<T>(null, new Exception(aS), FAILURE);
		return R;
	}

	public Exception failure() {
		return exc;
	}

	public Mode mode() {
		return mode;
	}

	public T success() {
		return succ;
	}
}

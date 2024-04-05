package tripleo.elijah_congenial_durable.advanced;

import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.UnintendedUseException;

public class Times {
	public static class Once implements T {
		@Override
		public Operation<Ok> call() {
			throw new UnintendedUseException();
			//return null;
		}
	}

	public interface T {
		Operation<Ok> call(); // ??
	}
}

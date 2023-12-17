package tripleo.elijah.stages.deduce;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

class Times {
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

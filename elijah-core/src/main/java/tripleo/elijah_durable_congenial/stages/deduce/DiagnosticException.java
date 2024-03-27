package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah.diagnostic.Diagnostic;

public class DiagnosticException extends Throwable {
	private final Diagnostic d;

	public DiagnosticException(final Diagnostic aD) {
		d = aD;
	}
}

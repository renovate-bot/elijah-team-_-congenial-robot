package tripleo.elijah_congenial.pipelines.write;

import org.jetbrains.annotations.Nullable;

public class WP_State_Control_1 implements WP_State_Control {
	private @Nullable Exception e;

	@Override
	public void clear() {
		e = null;
	}

	@Override
	public void exception(final Exception ee) {
		e = ee;
	}

	// TODO DiagnosticException
	@Override
	public Exception getException() {
		return e;
	}

	@Override
	public boolean hasException() {
		return e != null;
	}
}

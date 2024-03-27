package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.comp.i.IProgressSink;
import tripleo.elijah_durable_congenial.comp.i.ProgressSinkComponent;

public class DefaultProgressSink implements IProgressSink {
	@Override
	public void note(final Codes code,
					 final @NotNull ProgressSinkComponent component,
					 final int type,
					 final Object[] params) {
		//component.note(code, type, params);
		if (component.isPrintErr(code, type)) {
			final String s = component.printErr(code, type, params);
			SimplePrintLoggerToRemoveSoon.println_err_3(s);
		}
	}
}

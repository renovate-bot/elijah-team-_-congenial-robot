package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.i.ProgressSinkComponent;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

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

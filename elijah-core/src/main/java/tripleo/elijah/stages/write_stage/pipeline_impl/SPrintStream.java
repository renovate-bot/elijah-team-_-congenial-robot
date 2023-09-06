package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;

public class SPrintStream implements XPrintStream {
	private final StringBuilder sb = new StringBuilder();

	public @NotNull String getString() {
		return sb.toString();
	}

	@Override
	public void println(final String aS) {
		sb.append(aS);
		sb.append('\n');
	}
}

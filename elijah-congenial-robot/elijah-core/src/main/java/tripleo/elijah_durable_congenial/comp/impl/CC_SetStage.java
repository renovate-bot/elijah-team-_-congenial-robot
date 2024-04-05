package tripleo.elijah_durable_congenial.comp.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.Stages;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.CompilationChange;

public class CC_SetStage implements CompilationChange {
	private final String s;

	@Contract(pure = true)
	public CC_SetStage(final String aS) {
		s = aS;
	}

	@Override
	public void apply(final @NotNull Compilation c) {
		c.cfg().stage = Stages.valueOf(s);
	}
}

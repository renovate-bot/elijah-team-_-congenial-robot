package tripleo.elijah_durable_congenial.comp.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.CompilationChange;

public class CC_SetDoOut implements CompilationChange {
	private final boolean flag;

	public CC_SetDoOut(final boolean aB) {
		flag = aB;
	}

	@Override
	public void apply(final @NotNull Compilation c) {
		c.cfg().do_out = flag;
	}
}

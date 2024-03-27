package tripleo.elijah_durable_congenial.comp.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.CompilationChange;

public class CC_SetShowTree implements CompilationChange {
	private final boolean flag;

	public CC_SetShowTree(final boolean aB) {
		flag = aB;
	}

	@Override
	public void apply(final @NotNull Compilation c) {
		c.cfg().showTree = flag;
	}
}

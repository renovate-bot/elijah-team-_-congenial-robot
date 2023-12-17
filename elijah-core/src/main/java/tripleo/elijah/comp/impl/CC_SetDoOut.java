package tripleo.elijah.comp.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationChange;

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

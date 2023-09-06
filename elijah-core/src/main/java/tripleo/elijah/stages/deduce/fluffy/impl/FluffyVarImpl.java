package tripleo.elijah.stages.deduce.fluffy.impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.nextgen.composable.IComposable;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyVar;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyVarTarget;

public class FluffyVarImpl implements FluffyVar {
	@Override
	public @Nullable String name() {
		return null;
	}

	@Override
	public @Nullable IComposable nameComposable() {
		return null;
	}

	@Override
	public @Nullable Locatable nameLocatable() {
		return null;
	}

	@Override
	public @Nullable FluffyVarTarget target() {
		return null;
	}
}

package tripleo.elijah.stages.deduce.fluffy.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.nextgen.composable.IComposable;

public interface FluffyVar {
	String name();

	IComposable nameComposable();

	@Nullable Locatable nameLocatable();

	FluffyVarTarget target();
}

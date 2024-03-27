package tripleo.elijah_durable_congenial.stages.deduce.fluffy.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah_durable_congenial.nextgen.composable.IComposable;
import tripleo.elijah_durable_congenial.nextgen.composable.IComposable;

public interface FluffyVar {
	String name();

	IComposable nameComposable();

	@Nullable Locatable nameLocatable();

	FluffyVarTarget target();
}

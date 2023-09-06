package tripleo.elijah.stages.deduce.fluffy.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;

public interface FluffyComp {
	void find_multiple_items(final @NotNull OS_Module aModule);

	FluffyModule module(OS_Module aModule);
}

package tripleo.elijah_durable_congenial.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.comp.internal.CB_Output;
import tripleo.elijah_durable_congenial.comp.internal.CR_State;
import tripleo.elijah_durable_congenial.comp.internal.CompilationRunner;

public interface CR_Action {
	void attach(@NotNull CompilationRunner cr);

	@NotNull Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}

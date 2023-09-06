package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public interface CR_Action {
	void attach(@NotNull CompilationRunner cr);

	@NotNull Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}

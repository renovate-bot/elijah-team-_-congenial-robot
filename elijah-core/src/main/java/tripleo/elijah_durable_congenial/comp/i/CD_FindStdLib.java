package tripleo.elijah_durable_congenial.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.internal.CR_State;

import java.util.function.Consumer;

public interface CD_FindStdLib extends CompilerDriven {
	@NotNull Operation<Ok> findStdLib(CR_State crState, String aPreludeName, Consumer<Operation<CompilerInstructions>> coci);
}

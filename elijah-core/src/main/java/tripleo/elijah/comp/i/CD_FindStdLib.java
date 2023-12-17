package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import java.util.function.Consumer;

public interface CD_FindStdLib extends CompilerDriven {
	@NotNull Operation<Ok> findStdLib(CR_State crState, String aPreludeName, Consumer<Operation<CompilerInstructions>> coci);
}

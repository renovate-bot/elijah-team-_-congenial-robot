package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;

public interface CD_CompilationRunnerStart extends CompilerDriven {

	void start(final @NotNull CompilerInstructions aCompilerInstructions,
			   final @NotNull CR_State crState,
			   final @NotNull CB_Output out);
}

package tripleo.elijah_durable_congenial.comp.internal;

import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.IProgressSink;

import java.util.List;

public record CompilerBeginning(
		Compilation compilation,
		CompilerInstructions compilerInstructions,
		List<CompilerInput> compilerInput,
		IProgressSink progressSink,
		Compilation.CompilationConfig cfg
) {
}

package tripleo.elijah.comp.internal;

import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.IProgressSink;

import java.util.List;

public record CompilerBeginning(
		Compilation compilation,
		CompilerInstructions compilerInstructions,
		List<CompilerInput> compilerInput,
		IProgressSink progressSink,
		Compilation.CompilationConfig cfg
) {
}

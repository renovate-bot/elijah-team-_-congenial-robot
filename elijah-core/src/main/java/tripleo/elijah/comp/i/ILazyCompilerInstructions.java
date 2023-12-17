package tripleo.elijah.comp.i;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.internal.SourceFileParserParams;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;

import java.io.File;

public interface ILazyCompilerInstructions {
	@Contract(value = "_ -> new", pure = true)
	static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInstructions aCompilerInstructions) {
		return new ILazyCompilerInstructions() {
			@Override
			public CompilerInstructions get() {
				return aCompilerInstructions;
			}
		};
	}

	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInput input, final @NotNull CompilationClosure cc) {
		final String file_name = input.getInp();
		final File   f         = new File(file_name);

		return new ILazyCompilerInstructions() {
			@Override
			public CompilerInstructions get() {
				try {
					var file_name = f.getPath();

					var p = new SourceFileParserParams(input, f, file_name, cc);

					final Operation<CompilerInstructions> oci = cc.getCompilation().getCompilationEnclosure().getCompilationRunner().parseEzFile(p);

					if (oci.mode() == Mode.SUCCESS) {
						final CompilerInstructions parsed = oci.success();
						return parsed;
					} else {
						throw new RuntimeException(oci.failure()); // TODO ugh
					}
				} catch (Exception aE) {
					//return Operation.failure(aE);
					throw new RuntimeException(aE); // TODO ugh
				}
			}
		};
	}

	CompilerInstructions get();
}

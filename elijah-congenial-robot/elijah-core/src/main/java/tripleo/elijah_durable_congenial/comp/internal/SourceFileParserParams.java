package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.comp.i.CompilationClosure;

import java.io.File;

public record SourceFileParserParams(/*@NotNull*/ CompilerInput input, @NotNull File f, @NotNull String file_name,
												  @NotNull CompilationClosure cc) {
}

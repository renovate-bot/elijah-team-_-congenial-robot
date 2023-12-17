package tripleo.elijah.comp.queries;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.internal.SourceFileParserParams;
import tripleo.elijah.util.Operation;

public class QuerySourceFileParser {
	private final CompilationRunner cr;

	public QuerySourceFileParser(final CompilationRunner aCr) {
		cr = aCr;
	}

	public Operation<CompilerInstructions> process(final @NotNull SourceFileParserParams p) {
		final Operation<CompilerInstructions> oci = cr.realParseEzFile(p);
		return oci;
	}
}

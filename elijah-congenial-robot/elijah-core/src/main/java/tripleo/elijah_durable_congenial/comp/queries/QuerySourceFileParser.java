package tripleo.elijah_durable_congenial.comp.queries;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.internal.CompilationRunner;
import tripleo.elijah_durable_congenial.comp.internal.SourceFileParserParams;

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

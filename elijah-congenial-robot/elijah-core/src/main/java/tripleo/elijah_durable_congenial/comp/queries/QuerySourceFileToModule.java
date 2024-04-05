package tripleo.elijah_durable_congenial.comp.queries;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.internal.Out;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.query.QueryDatabase;
import tripleo.elijjah.ElijjahLexer;
import tripleo.elijjah.ElijjahParser;

import java.io.InputStream;

public class QuerySourceFileToModule {
	private final Compilation                   compilation;
	private final QuerySourceFileToModuleParams params;

	public QuerySourceFileToModule(final QuerySourceFileToModuleParams aParams, final Compilation aCompilation) {
		params      = aParams;
		compilation = aCompilation;
	}

	public @NotNull Operation<OS_Module> calculate() {
		final String      f      = params.sourceFilename();
		final InputStream s      = params.inputStream();
		final boolean     do_out = params.do_out();

		final ElijjahLexer lexer = new ElijjahLexer(s);
		lexer.setFilename(f);
		final ElijjahParser parser = new ElijjahParser(lexer);
		parser.out = new Out(f, compilation, do_out);
		parser.setFilename(f);
		try {
			parser.program();
		} catch (RecognitionException aE) {
			return Operation.failure(aE);
		} catch (TokenStreamException aE) {
			return Operation.failure(aE);
		}
		final OS_Module module = parser.out.module();
		parser.out = null;
		return Operation.success(module);
	}

	public OS_Module load(final QueryDatabase qb) {
		throw new NotImplementedException();
	}


}

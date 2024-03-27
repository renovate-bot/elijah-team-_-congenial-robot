package tripleo.elijah_durable_congenial.comp.queries;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.internal.CongenialEzFileProduct;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.query.QueryDatabase;
import tripleo.elijjah.EzLexer;
import tripleo.elijjah.EzParser;

import java.io.InputStream;

public class QueryEzFileToCompilerInstructions {
	private final QueryEzFileToModuleParams params;
	@Getter
	private CongenialEzFileProduct product;

	public QueryEzFileToCompilerInstructions(final QueryEzFileToModuleParams aParams) {
		params = aParams;
	}

	public @NotNull Operation<CompilerInstructions> calculate() {
		return calculateProduct().getOp();
	}

	public OS_Module load(final QueryDatabase qb) {
		throw new NotImplementedException();
	}

	public CongenialEzFileProduct calculateProduct() {
		if (product == null) {
			product = new _CongenialEzFileProduct();

		}
		return product;
	}

	private class _CongenialEzFileProduct implements CongenialEzFileProduct {
		private Operation<CompilerInstructions> op;

		@Override
		public CongenialEzFileProduct parse() {
			this.op = calculate();
			return this;
		}

		@Override
		public Operation<CompilerInstructions> getOp() {
			final String      f = params.sourceFilename;
			final InputStream s = params.inputStream;

			final EzLexer lexer = new EzLexer(s);
			lexer.setFilename(f);
			final EzParser parser = new EzParser(lexer);
			parser.setFilename(f);
			try {
				parser.program();
			} catch (RecognitionException | TokenStreamException aE) {
				return Operation.failure(aE);
			}
			final CompilerInstructions instructions = parser.ci;
			return Operation.success(instructions);
		}
	}
}

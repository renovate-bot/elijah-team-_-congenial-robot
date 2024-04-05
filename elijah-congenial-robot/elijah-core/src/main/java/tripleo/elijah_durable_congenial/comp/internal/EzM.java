package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.comp.Finally;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.queries.QueryEzFileToCompilerInstructions;
import tripleo.elijah_durable_congenial.comp.queries.QueryEzFileToModuleParams;
import tripleo.elijah_durable_congenial.util.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static tripleo.elijah.util.Mode.SUCCESS;

class EzM {
	@NotNull Operation<CompilerInstructions> parseEzFile1(final @NotNull SourceFileParserParams p) {
		@NotNull final File f = p.f();
		//logProgress(27, "   " + f.getAbsolutePath());
		if (!f.exists()) {


			p.cc().errSink().reportError(
					"File doesn't exist " + f.getAbsolutePath());


			return Operation.failure(new FileNotFoundException());

		} else {
			final Operation<CompilerInstructions> om = realParseEzFile(p);
			return om;
		}
	}

	private void logProgress(final int code, final String message) {
		final String x = "%d %s".formatted(code, message);
		System.out.println(x);
	}

	@NotNull Operation<CompilerInstructions> realParseEzFile(final @NotNull SourceFileParserParams p) {
		final String f    = p.file_name();
		final File   file = p.f();

		try {
			final InputStream                     s   = p.cc().io().readFile(file);
			final Operation<CompilerInstructions> oci = realParseEzFile(f, s, file, p.cc().getCompilation());

			if (/*false ||*/ oci.mode() == SUCCESS) {
				Operation<String> hash = Helpers.getHashForFilename(p.f().toString());
				//System.err.println("***** 166 " + hash.success());

				final CompilerInput input = p.input();

				// FIXME stdlib.ez will not get it's hash for eample 07/03
				if (input != null) {
					input.accept_hash(hash.success());
				} else {
					final Compilation c = p.cc().getCompilation();
					var REPORTS = c.reports();
					if (REPORTS.outputOn(Finally.Outs.Out_6262)) {
						System.err.println("***** 6262 " + f);
					}
				}
			}

			return oci;
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}

	@NotNull Operation<CompilerInstructions> realParseEzFile(final String f, final @Nullable InputStream s, final @NotNull File file, final @NotNull Compilation c) {
		final String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString(); // TODO 04/10 hash this and "attach"
			//queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
		} catch (IOException aE) {
			return Operation.failure(aE);
		}

		// TODO 04/10
		// Cache<CompilerInput, CompilerInstructions> fn2ci /*EzFileIdentity??*/(MAP/*??*/, resolver is try stmt)
		if (c.fn2ci().containsKey(absolutePath)) { // don't parse twice
			// TODO 04/10
			// ...queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ?? fnci
			return Operation.success(c.fn2ci().get(absolutePath));
		}

		try {
			final Operation<CompilerInstructions> cio = getEzFileProduct(f, s, c).parse().getOp();

			if (cio.mode() != SUCCESS) {
				final Exception e = cio.failure();
				assert e != null;

				SimplePrintLoggerToRemoveSoon.println_err_2(("parser exception: " + e));
				e.printStackTrace(System.err);
				//s.close();
				return cio;
			}

			final CompilerInstructions R = cio.success();
			R.setFilename(file.toString());
			c.fn2ci().put(absolutePath, R);
			return cio;
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException aE) {
					// TODO return inside finally: is this ok??
					return Operation.failure(aE);
				}
			}
		}
	}

	private CongenialEzFileProduct getEzFileProduct(final String aF, final InputStream aS, final Compilation aC) {
		return new CongenialEzFileProduct(){
			private Operation<CompilerInstructions> op;

			@Override
			public CongenialEzFileProduct parse() {
				this.op = parseEzFile_(aF, aS);
				return this;
			}

			@Override
			public Operation<CompilerInstructions> getOp() {
				return this.op;
			}
		};
	}

	private Operation<CompilerInstructions> parseEzFile_(final String f, final InputStream s) {
		final QueryEzFileToModuleParams qp = new QueryEzFileToModuleParams(f, s);
		return new QueryEzFileToCompilerInstructions(qp).calculate();
	}
}

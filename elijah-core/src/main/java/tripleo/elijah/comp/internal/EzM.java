package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.queries.QueryEzFileToModule;
import tripleo.elijah.comp.queries.QueryEzFileToModuleParams;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

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
					if (c.reports().outputOn(Finally.Outs.Out_6262)) {
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
			final Operation<CompilerInstructions> cio = parseEzFile_(f, s);

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

	private Operation<CompilerInstructions> parseEzFile_(final String f, final InputStream s) {
		final QueryEzFileToModuleParams qp = new QueryEzFileToModuleParams(f, s);
		return new QueryEzFileToModule(qp).calculate();
	}
}

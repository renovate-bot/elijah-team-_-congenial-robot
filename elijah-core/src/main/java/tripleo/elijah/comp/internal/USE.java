package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci.LibraryStatementPartImpl;
import tripleo.elijah.comp.CompFactory;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.comp.diagnostic.FileNotFoundDiagnostic;
import tripleo.elijah.comp.diagnostic.UnknownExceptionDiagnostic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.queries.QuerySourceFileToModule;
import tripleo.elijah.comp.queries.QuerySourceFileToModuleParams;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static tripleo.elijah.util.Mode.FAILURE;
import static tripleo.elijah.util.Mode.SUCCESS;

public class USE {
	private static final FilenameFilter         accept_source_files = new FilenameFilter() {
		@Override
		public boolean accept(final File directory, final String file_name) {
			final boolean matches = Pattern.matches(".+\\.elijah$", file_name)
					|| Pattern.matches(".+\\.elijjah$", file_name);
			return matches;
		}
	};
	private final        Compilation            c;
	private final        ErrSink                errSink;
	private final        Map<String, WorldModule> fn2m = new HashMap<String, WorldModule>();

	@Contract(pure = true)
	public USE(final Compilation aCompilation) {
		c       = aCompilation;
		errSink = c.getErrSink();
	}

	public void addModule(final OS_Module aModule, final String aFn) {
		final @NotNull CompilationEnclosure ce = c.getCompilationEnclosure();
		final WorldModule                   module = new DefaultWorldModule(aModule, ce);
		fn2m.put(aFn, module);
	}

	private Operation2<WorldModule> parseElijjahFile(final CompFactory.@NotNull InputRequest aInputRequest) {
		var owm = __parseElijjahFile(aInputRequest);
		aInputRequest.setOp(owm);
		return owm;
	}

	private Operation2<WorldModule> __parseElijjahFile(final CompFactory.@NotNull InputRequest aInputRequest) {
		final File f = aInputRequest.file();
		final LibraryStatementPart lsp = aInputRequest.lsp();

		//08/13 System.out.printf("   %s%n", f.getAbsolutePath());

		if (f.exists()) {
			final Operation2<WorldModule> om = realParseElijjahFile2(aInputRequest);

			if (om.mode() == SUCCESS) {
				// TODO we dont know which prelude to find yet
				final Operation2<WorldModule> pl = findPrelude(Compilation.CompilationAlways.defaultPrelude());

				// NOTE Go: infectious, tedious; also slightly lazy
				assert pl.mode() == SUCCESS;

				final WorldModule mm1 = om.success();
				final OS_Module   mm  = mm1.module();

				if (mm.getLsp() == null) {
					//assert mm.prelude  == null;
					mm.setLsp(lsp);
					mm.setPrelude(pl.success().module()); // FIXME 08/31
				}

				return Operation2.success(mm1);
			} else {
				// FIXME take a look at the later 06/19
				if (om.failure() instanceof ExceptionDiagnostic) {
					final Diagnostic e = om.failure();
					return Operation2.failure(e);
				}

				final Diagnostic e = new UnknownExceptionDiagnostic(om);
				return Operation2.failure(e);
			}
		} else {
			final Diagnostic e = new FileNotFoundDiagnostic(f);

			return Operation2.failure(e);
		}
	}

	public Operation2<WorldModule> realParseElijjahFile2(final CompFactory.@NotNull InputRequest aInputRequest) {
		final Operation<WorldModule> om;

		try {
			om = realParseElijjahFile(aInputRequest);
		} catch (Exception aE) {
			aE.printStackTrace();
			return Operation2.failure(new ExceptionDiagnostic(aE));
		}

		switch (om.mode()) {
		case SUCCESS:
			return Operation2.success(om.success());
		case FAILURE:
			final Exception e = om.failure();
			errSink.exception(e);
			return Operation2.failure(new ExceptionDiagnostic(e));
		default:
			throw new IllegalStateException("Unexpected value: " + om.mode());
		}
	}

	public Operation<WorldModule> realParseElijjahFile(final CompFactory.@NotNull InputRequest aInputRequest) {


		var file   = aInputRequest.file();
		var f      = aInputRequest.file().toString();
		var do_out = aInputRequest.do_out();


		try {
			final String absolutePath = file.getCanonicalFile().toString();
			if (fn2m.containsKey(absolutePath)) { // don't parse twice
				final WorldModule m = fn2m.get(absolutePath);
				return Operation.success(m);
			}

			final IO io = c.getIO();

			// tree add something

			final InputStream          s  = io.readFile(file);
			final Operation<OS_Module> om = parseFile_(f, s, do_out);
			if (om.mode() != SUCCESS) {
				final Exception e = om.failure();
				assert e != null;

				System.err.println("parser exception: " + e);
				e.printStackTrace(System.err);
				s.close();
				return Operation.failure(e);
			}

			@NotNull final CompilationEnclosure ce = c.getCompilationEnclosure();
			final WorldModule                   R = new DefaultWorldModule(om.success(), ce);
			fn2m.put(absolutePath, R);
			s.close();
			return Operation.success(R);
		} catch (IOException aE) {
			return Operation.failure(aE);
		}
	}

	private Operation<OS_Module> parseFile_(final String f, final InputStream s, final boolean do_out) {
		final QuerySourceFileToModuleParams qp = new QuerySourceFileToModuleParams(do_out, s, f);
		final QuerySourceFileToModule       q  = new QuerySourceFileToModule(qp, c);
		return q.calculate();
	}

	public Operation2<WorldModule> findPrelude(final String prelude_name) {
		final File local_prelude = new File("lib_elijjah/lib-" + prelude_name + "/Prelude.elijjah");

		if (!(local_prelude.exists())) {
			return Operation2.failure(new FileNotFoundDiagnostic(local_prelude));
		}

		final Operation2<WorldModule> om = realParseElijjahFile2(c.con().createInputRequest(local_prelude, c.cfg().do_out, null)); // TODO 09/05 fix null
		if (om.mode() == FAILURE) {
			om.failure().report(System.out);
			return om;
		}
		assert om.mode() == SUCCESS;
		return Operation2.success(om.success());
	}

	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) {
		final File instruction_dir = new File(compilerInstructions.getFilename()).getParentFile();
		for (final LibraryStatementPart lsp : compilerInstructions.lsps()) {
			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
			File         dir;
			if (dir_name.equals(".."))
				dir = instruction_dir/*.getAbsoluteFile()*/.getParentFile();
			else
				dir = new File(instruction_dir, dir_name);
			use_internal(dir, do_out, lsp);
		}
		final LibraryStatementPart lsp = new LibraryStatementPartImpl();
		lsp.setName(Helpers.makeToken("default")); // TODO: make sure this doesn't conflict
		lsp.setDirName(Helpers.makeToken(String.format("\"%s\"", instruction_dir)));
		lsp.setInstructions(compilerInstructions);
		use_internal(instruction_dir, do_out, lsp);
	}

	private void use_internal(final @NotNull File dir, final boolean do_out, @NotNull LibraryStatementPart lsp) {
		if (!dir.isDirectory()) {
			errSink.reportError("9997 Not a directory " + dir);
			return;
		}
		//
		final File[] files = dir.listFiles(accept_source_files);
		if (files != null) {
			for (final File file : files) {
				final CompFactory.InputRequest inp = c.con().createInputRequest(file, do_out, lsp);

				parseElijjahFile(inp);

				c.reports().addInput(inp, Finally.Out2.ELIJAH);
			}
		}
	}
}

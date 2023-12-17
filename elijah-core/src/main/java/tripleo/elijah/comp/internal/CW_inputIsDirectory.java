package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.diagnostic.TooManyEz_ActuallyNone;
import tripleo.elijah.comp.diagnostic.TooManyEz_BeSpecific;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.queries.QuerySearchEzFiles;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Operation2;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import static tripleo.elijah.util.Helpers.List_of;

public class CW_inputIsDirectory {
	public void apply(final @NotNull CompilerInput 				input,
					  final @NotNull Compilation 				c,
					  final @NotNull File 						f,
					  final @NotNull Consumer<CompilerInput> 	x) {
		CompilerInstructions ez_file;
		input.setDirectory(f);

		final List<CompilerInstructions> ezs = searchEzFiles(f, c.getCompilationClosure());

		switch (ezs.size()) {
		case 0:
			final Diagnostic d_toomany = new TooManyEz_ActuallyNone();
			final Maybe<ILazyCompilerInstructions> m = new Maybe<>(null, d_toomany);
			input.accept_ci(m);
			x.accept(input);
			break;
		case 1:
			ez_file = ezs.get(0);
			final ILazyCompilerInstructions ilci = ILazyCompilerInstructions.of(ez_file);
			final Maybe<ILazyCompilerInstructions> m3 = new Maybe<>(ilci, null);
			input.accept_ci(m3);
			x.accept(input);
			break;
		default:
			//final Diagnostic d_toomany = new TooManyEz_UseFirst();
			//add_ci(ezs.get(0));

			// more than 1 (negative is not possible)
			final Diagnostic d_toomany2 = new TooManyEz_BeSpecific();
			final Maybe<ILazyCompilerInstructions> m2 = new Maybe<>(null, d_toomany2);
			input.accept_ci(m2);
			x.accept(input);
			break;
		}

		c.reports().addInput(input, Finally.Out2.EZ);
	}

	private List<CompilerInstructions> searchEzFiles(final @NotNull File directory, final @NotNull CompilationClosure ccl) {
		final QuerySearchEzFiles                     q    = new QuerySearchEzFiles(ccl);
		final Operation2<List<CompilerInstructions>> olci = q.process(directory);

		switch (olci.mode()) {
		case SUCCESS -> {
			return olci.success();
		}
		case FAILURE -> {
			ccl.errSink().reportDiagnostic(olci.failure());
			return List_of();
		}
		default -> throw new IllegalStateException("Unexpected value: " + olci.mode());
		}
	}
}

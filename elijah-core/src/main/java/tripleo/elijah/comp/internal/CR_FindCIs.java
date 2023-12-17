package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.stateful.State;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CR_FindCIs extends DefaultStateful implements CR_Action {
	private final @NotNull List<CompilerInput> inputs;
	private final @NotNull CCI                 cci;
	private final @NotNull IProgressSink       _ps;

	public CR_FindCIs(final @NotNull CompilerBeginning beginning) {
		State st = CompilationRunner.ST.INITIAL; // que?? 07/01

		inputs = beginning.compilerInput();

		var comp = beginning.compilation();
		var progressSink = beginning.progressSink();

		// TODO 09/05 look at 2 different progressSinks
		cci = new DefaultCCI(comp, comp._cis(), progressSink);
		_ps = comp.getCompilationEnclosure().getCompilationBus().defaultProgressSink();

		//eventualCR_FindCIs.resolve(this);
	}

	@Override
	public @NotNull Operation<Ok> execute(final @NotNull CR_State st, final @NotNull CB_Output aO) {
		final Compilation c = st.ca().getCompilation();

		final List<CompilerInput> x = find_cis(inputs, c, c.getErrSink());
		for (final CompilerInput compilerInput : x) {
			cci.accept(compilerInput.acceptance_ci(), _ps);
		}

		return Operation.success(Ok.instance());
	}

	@Override
	public void attach(final @NotNull CompilationRunner cr) {
	}

	protected @NotNull List<CompilerInput> find_cis(final @NotNull List<CompilerInput> inputs,
													final @NotNull Compilation c,
													final @NotNull ErrSink errSink) {
		final List<CompilerInput> x = new ArrayList<>();

		for (final CompilerInput input : inputs) {
			_processInput(c, errSink, x, input);
		}

		return x;
	}

	private void _processInput(final @NotNull Compilation c,
							   final @NotNull ErrSink errSink,
							   final @NotNull List<CompilerInput> x,
							   final @NotNull CompilerInput input) {
		CompilerInstructions ez_file;
		switch (input.ty()) {
		case NULL -> {
		}
		case SOURCE_ROOT -> {
		}
		default -> {
			return;
		}
		}

		final String  file_name = input.getInp();
		final File    f         = new File(file_name);
		final boolean matches2  = Pattern.matches(".+\\.ez$", file_name);
		if (matches2) {
			final ILazyCompilerInstructions ilci = ILazyCompilerInstructions.of(input, c.getCompilationClosure());

			final Maybe<ILazyCompilerInstructions> m4 = new Maybe<>(ilci, null);
			input.accept_ci(m4);
			x.add(input);
		} else {
			//errSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				_inputIsDirectory(c, x, input, f);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				errSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	private void _inputIsDirectory(final @NotNull Compilation c,
								   final @NotNull List<CompilerInput> x,
								   final @NotNull CompilerInput input,
								   final @NotNull File f) {
		new CW_inputIsDirectory().apply(input, c, f, (final @NotNull CompilerInput inp) -> x.add(inp));
	}

	@Override
	public @NotNull String name() {
		return "find cis";
	}
}

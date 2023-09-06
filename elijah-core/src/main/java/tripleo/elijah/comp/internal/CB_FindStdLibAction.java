package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.query.Mode;
import tripleo.elijah.util.Operation;

import java.util.ArrayList;
import java.util.List;

class CB_FindStdLibAction implements ICompilationBus.CB_Action {
	private final     CompilationEnclosure  ce;
	private final     CR_State              crState;
	private final     List<CB_OutputString> o = new ArrayList<>(); // FIXME 07/01 how is this modified?
	private @Nullable CD_FindStdLib         findStdLib;

	public CB_FindStdLibAction(final CompilationEnclosure aCe, final CR_State aCrState) {
		ce      = aCe;
		crState = aCrState;

		//findStdLib =
		obtain();
	}

	private void obtain() {
		final Operation<CompilerDriven> x = ce.getCompilationDriver().get(Compilation.CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2);

		if (x.mode() == Mode.SUCCESS) {
			findStdLib = (CD_FindStdLib) x.success();
		}
	}

	@Override
	public void execute() {
		final String preludeName = Compilation.CompilationAlways.defaultPrelude();

		if (findStdLib != null)
			findStdLib.findStdLib(crState, preludeName, this::getPushItem);
	}

	private void getPushItem(final @NotNull Operation<CompilerInstructions> oci) {
		if (oci.mode() == Mode.SUCCESS) {
			final Compilation c = ce.getCompilation();

			c.pushItem(oci.success());
			c.use(oci.success(), true);
		} else {
			throw new IllegalStateException(oci.failure());
		}
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "find std lib";
	}

	@Contract(pure = true)
	@Override
	public @NotNull List<CB_OutputString> outputStrings() {
		return o;
	}

	@Contract(value = " -> new", pure = true)
	public ICompilationBus.@NotNull CB_Process process() {
		return new CompilationBus.SingleActionProcess(this);
	}
}

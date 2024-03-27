package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.i.*;

import java.util.List;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

class CB_StartCompilationRunnerAction implements CB_Action, CB_Process {
	private final          CompilationRunner    compilationRunner;
	private final          CompilerInstructions ci;
	private final @NotNull IPipelineAccess      pa;

	@Contract(pure = true)
	public CB_StartCompilationRunnerAction(final CompilationRunner aCompilationRunner,
										   final @NotNull IPipelineAccess aPa,
										   final CompilerInstructions aCi) {
		compilationRunner = aCompilationRunner;
		pa                = aPa;
		ci                = aCi;

		o = pa.getCompilationEnclosure().getCB_Output(); //new CB_Output();
	}

	@Contract(value = " -> new", pure = true)
	@NotNull
	public CB_Process cb_Process() {
		return this;
	}

	@Override
	@NotNull
	public List<CB_Action> steps() {
		return List_of(
				CB_StartCompilationRunnerAction.this
					  );
	}

	@Override
	public void execute() {
		final CompilerDriver compilationDriver = pa
				.getCompilationEnclosure()
				.getCompilationDriver();
		final Operation<CompilerDriven> ocrsd = compilationDriver.get(Compilation.CompilationAlways.Tokens.COMPILATION_RUNNER_START);

		switch (ocrsd.mode()) {
		case SUCCESS -> {
			final CD_CompilationRunnerStart compilationRunnerStart = (CD_CompilationRunnerStart) ocrsd.success();

			compilationRunnerStart.start(ci, compilationRunner.crState, o);
		}
		case FAILURE, NOTHING -> throw new IllegalStateException("Error");
		}
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "StartCompilationRunnerAction";
	}

	@Contract(pure = true)
	@Override
	public @Nullable List<CB_OutputString> outputStrings() {
		return o.get();
	}

	private final CB_Output o;
}

package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.PipelineMember;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah_congenial.pipelines.comp_runner.CongenialRunnerState;
import tripleo.elijah_congenial.pipelines.comp_runner.CongenialRunnerStateImpl;

public class CR_State {
	private final CongenialRunnerState carrier;

	@Contract(pure = true)
	public CR_State(ICompilationAccess aCa) {
		carrier = new CongenialRunnerStateImpl(aCa);
	}

	public ICompilationAccess ca() {
		return carrier.ca();
	}

	public CompilationEnclosure ce() {
		return carrier.ce();
	}

	public CompilationRunner runner() {
		return carrier.runner();
	}

	public void setRunner(CompilationRunner aCompilationRunner) {
		carrier.setRunner(aCompilationRunner);
	}

	public ProcessRecord _access_pr() {
		return carrier._access_pr();
	}

	public boolean started() {
		return carrier.started();
	}

	public void set_started() {
		carrier.set_started();
	}

	public interface PipelinePlugin {
		PipelineMember instance(final @NotNull AccessBus ab0);

		String name();
	}
}

package tripleo.elijah_congenial.pipelines.comp_runner;

import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.internal.CompilationRunner;

public class CongenialRunnerStateImpl implements CongenialRunnerState {
	//private CB_Action          cur;
	//public  RuntimeProcesses rt;

	private       boolean            started;

	private final ICompilationAccess ca;
	private final ProcessRecord      pr;
	private       CompilationRunner  compilationRunner;

	public CongenialRunnerStateImpl(final ICompilationAccess aCa) {
		ca = aCa;

		//noinspection TypeMayBeWeakened
		final ProcessRecord_PipelineAccess pipelineAccess = new ProcessRecord_PipelineAccess(ca);
		ca.getCompilation().getCompilationEnclosure().provide(pipelineAccess);

		// congenial-compilation-shared-state.set-created(pr) ...
		pr = new ProcessRecordImpl(ca);
	}

	@Override
	public ICompilationAccess ca() {
		return ca;
	}
	@Override public CompilationEnclosure ce() {
		return ca.getCompilation().getCompilationEnclosure();
	}

	@Override
	public CompilationRunner runner() {
		return compilationRunner;
	}

	@Override
	public void setRunner(CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;
	}

	@Override
	public ProcessRecord _access_pr() {
		return pr;
	}

	@Override
	public void set_started() {
		started = true;
	}

	@Override
	public boolean started() {
		return started;
	}
}

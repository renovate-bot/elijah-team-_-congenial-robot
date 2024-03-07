package tripleo.elijah_congenial.pipelines.comp_runner;

import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.internal.CompilationRunner;

public interface CongenialRunnerState {
	ICompilationAccess ca();

	CompilationEnclosure ce();

	CompilationRunner runner();

	void setRunner(CompilationRunner aCompilationRunner);

	ProcessRecord _access_pr();

	void set_started();

	boolean started();
}

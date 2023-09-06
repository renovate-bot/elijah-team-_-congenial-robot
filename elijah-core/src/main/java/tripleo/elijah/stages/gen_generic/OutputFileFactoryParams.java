package tripleo.elijah.stages.gen_generic;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.logging.ElLog;

public class OutputFileFactoryParams {
	private final CompilationEnclosure compilationEnclsure;
	private final ErrSink              errSink;
	private final OS_Module            mod;
	private final ElLog.Verbosity      verbosity;

	@Contract(pure = true)
	public OutputFileFactoryParams(final OS_Module aMod,
								   final CompilationEnclosure aCompilationEnclsure) {
		mod                 = aMod;
		compilationEnclsure = aCompilationEnclsure;
		//
		errSink   = compilationEnclsure.getCompilationClosure().errSink();
		verbosity = compilationEnclsure.getCompilationAccess().testSilence();
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclsure;
	}

	public ErrSink getErrSink() {
		return errSink;
	}

	public OS_Module getMod() {
		return mod;
	}

	public PipelineLogic getPipelineLogic() {
		return getCompilationEnclosure().getPipelineLogic();
	}

	public ElLog.Verbosity getVerbosity() {
		return verbosity;
	}
}

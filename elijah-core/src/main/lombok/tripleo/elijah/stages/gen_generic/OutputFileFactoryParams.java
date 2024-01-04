package tripleo.elijah.stages.gen_generic;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.logging.ElLog;

@Getter
public class OutputFileFactoryParams {
	private final CompilationEnclosure compilationEnclosure;
	private final OS_Module            mod;

	@Contract(pure = true)
	public OutputFileFactoryParams(final OS_Module aMod,
								   final CompilationEnclosure aCompilationEnclosure) {
		mod                  = aMod;
		compilationEnclosure = aCompilationEnclosure;
	}

	public PipelineLogic getPipelineLogic() {
		return getCompilationEnclosure().getPipelineLogic();
	}

	public ElLog.Verbosity getVerbosity() {
		return compilationEnclosure.getCompilationAccess().testSilence();
	}

	public ErrSink getErrSink() {
		return compilationEnclosure.getCompilationClosure().errSink();
	}

	public CompilationEnclosure getCompilationEnclosure() {
		// antilombok
		return compilationEnclosure;
	}

	public OS_Module getMod() {
		// antilombok
		return mod;
	}
}

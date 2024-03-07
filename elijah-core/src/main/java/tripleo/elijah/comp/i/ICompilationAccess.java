package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Pipeline;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.PipelineMember;
import tripleo.elijah.comp.Stages;
import tripleo.elijah.stages.deduce.IFunctionMapHook;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util._Extensionable;

import java.util.List;

public interface ICompilationAccess extends _Extensionable {
	void addFunctionMapHook(IFunctionMapHook aFunctionMapHook);

	void addPipeline(final PipelineMember pl);

	List<IFunctionMapHook> functionMapHooks();

	Compilation getCompilation();

	Stages getStage();

	void setPipelineLogic(final PipelineLogic pl);

	ElLog.Verbosity testSilence();

	void writeLogs();

	Pipeline internal_pipelines();
}

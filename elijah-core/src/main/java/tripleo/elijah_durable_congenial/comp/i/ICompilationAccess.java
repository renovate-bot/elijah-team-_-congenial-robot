package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_congenial.comp.Pipeline;
import tripleo.elijah_durable_congenial.comp.PipelineMember;
import tripleo.elijah_durable_congenial.comp.Stages;
import tripleo.elijah_durable_congenial.stages.deduce.IFunctionMapHook;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

import java.util.List;

public interface ICompilationAccess {
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

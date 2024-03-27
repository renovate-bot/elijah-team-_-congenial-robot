package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah.comp.PipelineLogic;

public interface ProcessRecord {
	ICompilationAccess ca();

	IPipelineAccess pa();

	PipelineLogic pipelineLogic();

	void writeLogs();
}

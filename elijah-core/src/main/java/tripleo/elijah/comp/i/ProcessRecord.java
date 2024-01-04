package tripleo.elijah.comp.i;

import tripleo.elijah.comp.PipelineLogic;

public interface ProcessRecord {
	ICompilationAccess ca();

	IPipelineAccess pa();

	PipelineLogic pipelineLogic();

	void writeLogs();
}

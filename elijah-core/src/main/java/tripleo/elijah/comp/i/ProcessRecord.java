package tripleo.elijah.comp.i;

import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.PipelineLogic;

public interface ProcessRecord {
	AccessBus ab();

	ICompilationAccess ca();

	IPipelineAccess pa();

	PipelineLogic pipelineLogic();

	void writeLogs();
}
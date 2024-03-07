package tripleo.elijah.comp.functionality.f291;

import tripleo.elijah.comp.i.ICompilationAccess2;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;

import java.util.List;

public record WPIS_GenerateOutputsFinalization(
		List<NG_OutputRequest> outputRequestList,
		OutputStrategyC outputStrategyC,
		ICompilationAccess2 compilationAccess2) {
}

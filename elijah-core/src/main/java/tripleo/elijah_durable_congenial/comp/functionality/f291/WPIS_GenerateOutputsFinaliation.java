package tripleo.elijah_durable_congenial.comp.functionality.f291;

import tripleo.elijah_congenial.pipelines.write.NG_OutputRequest;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess2;
import tripleo.elijah_durable_congenial.stages.generate.OutputStrategyC;

public record WPIS_GenerateOutputsFinaliation(
		java.util.List<NG_OutputRequest> aOutputRequestList,
		OutputStrategyC aOutputStrategyC,
		ICompilationAccess2 aCompilationAccess2) {
}

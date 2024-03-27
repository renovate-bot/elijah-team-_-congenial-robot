package tripleo.elijah_durable_congenial.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.generate.OutputStrategyC;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.generate.OutputStrategyC;

import java.util.List;

public interface NG_OutputItem {
	@NotNull List<NG_OutputStatement> getOutputs();

	EOT_FileNameProvider outName(OutputStrategyC aOutputStrategyC, final GenerateResult.TY ty);
}

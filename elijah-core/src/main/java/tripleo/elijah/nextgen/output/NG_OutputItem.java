package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.generate.OutputStrategyC;

import java.util.List;

public interface NG_OutputItem {
	@NotNull List<NG_OutputStatement> getOutputs();

	EOT_OutputFile.FileNameProvider outName(OutputStrategyC aOutputStrategyC, final GenerateResult.TY ty);
}

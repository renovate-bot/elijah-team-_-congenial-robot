package tripleo.elijah.stages.write_stage.pipeline_impl;

import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.nextgen.output.NG_OutputStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;

// TODO 09/04 Duplication madness
public record NG_OutputRequest(
		String fileName,
		EG_Statement statement,
		NG_OutputStatement outputStatment,
		NG_OutputItem outputItem
) {
}

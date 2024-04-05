package tripleo.elijah_durable_congenial.nextgen.output;

import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult.TY;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;

public interface NG_OutputStatement extends EG_Statement {

	GenerateResult.TY getTy();

	EIT_ModuleInput getModuleInput();

	// promise filename
	// promise EOT_OutputFile
}

package tripleo.elijah_durable_congenial.stages.gen_c;

import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.util.buffer.Buffer;

public interface C2C_Result {
	Buffer getBuffer();

	EG_Statement getStatement();

	GenerateResult.TY ty();

	OS_Module getDefinedModule();

	WhyNotGarish_BaseFunction getWhyNotGarishFunction();
}

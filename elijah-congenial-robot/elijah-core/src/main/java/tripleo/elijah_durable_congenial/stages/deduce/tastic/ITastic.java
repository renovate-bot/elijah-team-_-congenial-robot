package tripleo.elijah_durable_congenial.stages.deduce.tastic;

import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;

public interface ITastic {
	void do_assign_call(BaseEvaFunction aGeneratedFunction, Context aFdCtx, IdentTableEntry aIdte, int aIndex);

	void do_assign_call(BaseEvaFunction aGeneratedFunction, Context aContext, VariableTableEntry aVte, Instruction aInstruction, final OS_Element aName);
}

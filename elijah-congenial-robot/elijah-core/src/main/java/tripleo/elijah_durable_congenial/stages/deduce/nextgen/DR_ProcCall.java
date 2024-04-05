package tripleo.elijah_durable_congenial.stages.deduce.nextgen;

import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ProcTableEntry;
import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ProcTableEntry;

public class DR_ProcCall implements DR_Item {
	private final IExpression     z;
	private final ProcTableEntry  pte;
	private final BaseEvaFunction baseEvaFunction;

	public DR_ProcCall(final IExpression aZ, final ProcTableEntry aPte, final BaseEvaFunction aBaseEvaFunction) {
		z               = aZ;
		pte             = aPte;
		baseEvaFunction = aBaseEvaFunction;
	}

	public FunctionInvocation getFunctionInvocation() {
		return pte.getFunctionInvocation();
	}
}

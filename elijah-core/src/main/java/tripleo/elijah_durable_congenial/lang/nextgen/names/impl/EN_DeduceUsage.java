package tripleo.elijah_durable_congenial.lang.nextgen.names.impl;

import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;

public class EN_DeduceUsage implements EN_Usage {
	private final InstructionArgument backlink;
	private final BaseEvaFunction     evaFunction;
	private       BaseTableEntry      bte;

	public EN_DeduceUsage(final InstructionArgument aBacklink, BaseEvaFunction evaFunction, BaseTableEntry aTableEntry) {
		backlink         = aBacklink;
		this.evaFunction = evaFunction;
		this.bte         = aTableEntry;
	}

	public BaseTableEntry getBte() {
		return bte;
	}

	public void setBte(BaseTableEntry aBte) {
		bte = aBte;
	}
}

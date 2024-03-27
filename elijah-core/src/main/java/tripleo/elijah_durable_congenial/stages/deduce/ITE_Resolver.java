package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;

public interface ITE_Resolver {
	boolean isDone();

	void check();

	IdentTableEntry.ITE_Resolver_Result getResult();
}

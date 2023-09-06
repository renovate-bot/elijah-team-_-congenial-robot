package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.IdentTableEntry;

public interface ITE_Resolver {
	boolean isDone();

	void check();

	IdentTableEntry.ITE_Resolver_Result getResult();
}

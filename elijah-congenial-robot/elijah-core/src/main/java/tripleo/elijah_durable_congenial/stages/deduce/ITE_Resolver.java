package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.stages.gen_fn.*;

public interface ITE_Resolver {
	boolean isDone();

	void check();

	ITE_Resolver_Result getResult();
}

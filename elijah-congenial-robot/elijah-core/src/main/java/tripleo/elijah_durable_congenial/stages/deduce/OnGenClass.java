package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;

@FunctionalInterface
public interface OnGenClass {
	void accept(final EvaClass aGeneratedClass);
}

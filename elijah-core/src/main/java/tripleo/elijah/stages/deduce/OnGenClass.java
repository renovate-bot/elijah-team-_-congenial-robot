package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.EvaClass;

@FunctionalInterface
public interface OnGenClass {
	void accept(final EvaClass aGeneratedClass);
}

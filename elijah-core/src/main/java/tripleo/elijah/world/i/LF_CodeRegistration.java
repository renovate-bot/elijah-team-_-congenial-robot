package tripleo.elijah.world.i;

import tripleo.elijah.Eventual;
import tripleo.elijah.stages.gen_fn.EvaFunction;

public interface LF_CodeRegistration {
	void accept(final EvaFunction aEvaFunction, final Eventual<Integer> aCodeCallback);
}

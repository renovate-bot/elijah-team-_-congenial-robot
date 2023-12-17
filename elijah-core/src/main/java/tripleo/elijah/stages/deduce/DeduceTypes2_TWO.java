package tripleo.elijah.stages.deduce;

import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.stages.gen_c.DeducedBaseEvaFunction;
import tripleo.elijah.stages.gen_c.DefaultDeducedBaseEvaFunction;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaFunction;

import java.util.function.Consumer;

public class DeduceTypes2_TWO extends DeduceTypes2 {
	public DeduceTypes2_TWO(final DeduceTypes2Request aDeduceTypes2Request) {
		super(aDeduceTypes2Request);
	}

	public void pass(final BaseEvaFunction aEvaFunction, final Consumer<DeducedBaseEvaFunction> c) {
		deduceOneFunction((EvaFunction) aEvaFunction, this._phase());
		c.accept(new DefaultDeducedBaseEvaFunction(aEvaFunction));
	}
}

package tripleo.elijah.stages.pp;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.stages.gen_c.DeducedBaseEvaFunction;
import tripleo.elijah.stages.gen_c.DeducedEvaConstructor;

import java.util.function.Consumer;

public class PP_Constructor implements IPP_Constructor {
	private final DeducedEvaConstructor carrier;

	public PP_Constructor(final DeducedEvaConstructor aGf) {
		carrier = aGf;
	}

	public PP_Constructor(final DeducedEvaConstructor aDeduced, final Consumer<DeducedBaseEvaFunction> aDeducedBaseEvaFunctionConsumer) {
		throw new UnintendedUseException();
	}

	@Override
	public DeducedEvaConstructor get2Carrier() {
		return carrier;
	}
}

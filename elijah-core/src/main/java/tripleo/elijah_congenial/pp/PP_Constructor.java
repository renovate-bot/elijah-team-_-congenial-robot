package tripleo.elijah_congenial.pp;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah_durable_congenial.stages.gen_c.DeducedBaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_c.DeducedEvaConstructor;

import java.util.function.Consumer;

public class PP_Constructor implements IPP_Constructor {
	private final DeducedEvaConstructor carrier;

	public PP_Constructor(final DeducedEvaConstructor aDeducedEvaConstructor) {
		carrier = aDeducedEvaConstructor;
	}

	public PP_Constructor(final DeducedEvaConstructor aDeducedEvaConstructor, final Consumer<DeducedBaseEvaFunction> aDeducedBaseEvaFunctionConsumer) {
		throw new UnintendedUseException();
	}

	@Override
	public DeducedEvaConstructor get2Carrier() {
		return carrier;
	}
}

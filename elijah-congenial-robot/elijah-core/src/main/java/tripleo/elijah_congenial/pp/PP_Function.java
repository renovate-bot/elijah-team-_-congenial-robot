package tripleo.elijah_congenial.pp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah_durable_congenial.stages.gen_c.DeducedBaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;

import java.util.function.Consumer;

public class PP_Function implements IPP_Function {
	private final DeducedBaseEvaFunction carrier;

	public PP_Function(final @NotNull DeducedBaseEvaFunction aGf) {
		carrier = aGf;
	}

	public PP_Function(final DeducedBaseEvaFunction aDeduced, final Consumer<DeducedBaseEvaFunction> aO) {
		throw new UnintendedUseException();
	}

	public BaseEvaFunction getCarrier() {
		return (BaseEvaFunction) carrier.getCarrier();
	}

	@Override
	public DeducedBaseEvaFunction get2Carrier() {
		return carrier;
	}
}

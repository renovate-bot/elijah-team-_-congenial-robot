package tripleo.elijah.stages.pp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.lang.i.ClassStatement;

import java.util.function.Consumer;

public class PP_Class implements IPP_Class {
	private DeducedEvaClass carrier;

	public PP_Class(final @NotNull DeducedEvaClass aGf) {
		carrier = aGf;
	}

	public PP_Class(final DeducedEvaClass aDeduced, final Consumer<DeducedEvaClass> aO) {
		throw new UnintendedUseException();
	}

	@Override
	public ClassStatement getCarrier() {
		return carrier.getCarrier().getKlass();
	}

	@Override
	public DeducedEvaClass get2Carrier() {
		return carrier;
	}
}

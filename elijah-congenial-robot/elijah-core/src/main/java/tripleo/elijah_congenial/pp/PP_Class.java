package tripleo.elijah_congenial.pp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;

import java.util.function.Consumer;

public class PP_Class implements IPP_Class {
	private final DeducedEvaClass carrier;

	public PP_Class(final @NotNull DeducedEvaClass aDeducedEvaClass) {
		carrier = aDeducedEvaClass;
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

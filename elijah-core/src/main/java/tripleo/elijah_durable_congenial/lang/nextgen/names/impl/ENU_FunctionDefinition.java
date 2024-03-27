package tripleo.elijah_durable_congenial.lang.nextgen.names.impl;

import tripleo.elijah_durable_congenial.lang.impl.FunctionDefImpl;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Understanding;

public class ENU_FunctionDefinition implements EN_Understanding {
	private final FunctionDefImpl carrier;

	public ENU_FunctionDefinition(final FunctionDefImpl aFunctionDef) {
		carrier = aFunctionDef;
	}

	public FunctionDefImpl getCarrier() {
		return carrier;
	}
}

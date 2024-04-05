package tripleo.elijah_durable_congenial.comp.internal;

import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;

public interface CongenialEzFileProduct {
	CongenialEzFileProduct parse();

	Operation<CompilerInstructions> getOp();
}

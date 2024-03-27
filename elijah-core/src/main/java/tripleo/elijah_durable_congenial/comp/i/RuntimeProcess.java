package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah_durable_congenial.comp.internal.CB_Output;
import tripleo.elijah_durable_congenial.comp.internal.CR_State;

public interface RuntimeProcess {
	void postProcess();

	void prepare() throws Exception;

	void run(final Compilation aComp, CR_State st, CB_Output output);
}

package tripleo.elijah_durable_congenial.comp.internal;

import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess;
import tripleo.elijah_durable_congenial.comp.i.ProcessRecord;
import tripleo.elijah_durable_congenial.comp.i.RuntimeProcess;

public final class EmptyProcess implements RuntimeProcess {
	public EmptyProcess(final ICompilationAccess aCompilationAccess, final ProcessRecord aPr) {
	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() {
	}

	@Override
	public void run(final Compilation aComp, final CR_State st, final CB_Output output) {

	}
}

package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.Contract;
import tripleo.elijah_durable_congenial.comp.Stages;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess;
import tripleo.elijah_durable_congenial.comp.i.ProcessRecord;
import tripleo.elijah_durable_congenial.comp.i.RuntimeProcess;

public class DStageProcess implements RuntimeProcess {
	private final ICompilationAccess ca;
	private final ProcessRecord      pr;

	@Contract(pure = true)
	public DStageProcess(final ICompilationAccess aCa, final ProcessRecord aPr) {
		ca = aCa;
		pr = aPr;
	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() {
		assert ca.getStage() == Stages.D;
	}

	@Override
	public void run(final Compilation aComp, final CR_State st, final CB_Output output) {

	}
}

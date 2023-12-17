package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Stages;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.i.RuntimeProcess;

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

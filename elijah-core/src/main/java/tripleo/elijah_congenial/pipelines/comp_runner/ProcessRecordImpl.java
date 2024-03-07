package tripleo.elijah_congenial.pipelines.comp_runner;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah_congenial.anno.ElLateInit;

public class ProcessRecordImpl implements ProcessRecord {
	private final @NotNull ICompilationAccess ca;
	@ElLateInit
	private                IPipelineAccess    pa;
	@ElLateInit
	private                PipelineLogic      pipelineLogic;

	public ProcessRecordImpl(final @NotNull ICompilationAccess ca0) {
		ca = ca0;

		ca.getCompilation().getCompilationEnclosure().pipelineAccessPromise.then(apa -> {
			pa = apa;
			pipelineLogic = new PipelineLogic(pa, ca);
		});
	}

	@Contract(pure = true)
	@Override
	public ICompilationAccess ca() {
		return ca;
	}

	@Contract(pure = true)
	@Override
	public PipelineLogic pipelineLogic() {
		return pipelineLogic;
	}

	@Override
	public void writeLogs() {
		ca.getCompilation().cfg().stage.writeLogs(ca);
	}
}

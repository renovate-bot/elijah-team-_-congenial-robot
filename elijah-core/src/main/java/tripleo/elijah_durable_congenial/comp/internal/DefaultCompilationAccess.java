package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_congenial.comp.Pipeline;
import tripleo.elijah_durable_congenial.comp.PipelineMember;
import tripleo.elijah_durable_congenial.comp.Stages;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess;
import tripleo.elijah_durable_congenial.stages.deduce.IFunctionMapHook;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

import java.util.List;

public class DefaultCompilationAccess implements ICompilationAccess {
	protected final Compilation compilation;
	private final   Pipeline    pipelines = new Pipeline();

	@Contract(pure = true)
	public DefaultCompilationAccess(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		compilation.getCompilationEnclosure().getPipelineLogic().dp.addFunctionMapHook(aFunctionMapHook);
	}

	@Override
	public void addPipeline(final PipelineMember pl) {
		pipelines.add(pl);
	}

	@Override
	public @NotNull List<IFunctionMapHook> functionMapHooks() {
		return compilation.getCompilationEnclosure().getPipelineLogic().dp.functionMapHooks;
	}

	@Override
	public Compilation getCompilation() {
		return compilation;
	}

	@Override
	public @NotNull Stages getStage() {
		return Stages.O;
	}

	@Override
	public void setPipelineLogic(final PipelineLogic pl) {
		assert compilation.getCompilationEnclosure().getPipelineLogic() == null;
		compilation.getCompilationEnclosure().setPipelineLogic(pl);
	}

	@Override
	@NotNull
	public ElLog.Verbosity testSilence() {
		return compilation.cfg().silent ? ElLog.Verbosity.SILENT : ElLog.Verbosity.VERBOSE;
	}

	@Override
	public void writeLogs() {
		final CompilationEnclosure ce            = compilation.getCompilationEnclosure();
		ce.writeLogs();
	}

	@Override
	public @NotNull Pipeline internal_pipelines() {
		return pipelines;
	}
}

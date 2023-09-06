package tripleo.elijah.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.lang.i.OS_Module;

import java.util.List;

class PL_AddModules implements PipelineLogicRunnable {
	private final List<OS_Module> ml;

	@Contract(pure = true)
	public PL_AddModules(final List<OS_Module> aModule) {
		ml = aModule;
	}

	@Override
	public void run(final @NotNull PipelineLogic pipelineLogic) {
		for (final OS_Module m : ml) {
			pipelineLogic.addModule(m);
		}
	}
}

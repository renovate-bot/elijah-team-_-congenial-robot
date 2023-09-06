package tripleo.elijah.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

@SuppressWarnings("unused")
public record GM_GenerateModuleResult(GenerateResult generateResult,
									  GN_GenerateNodesIntoSink generateNodesIntoSink,
									  GM_GenerateModuleRequest generateModuleRequest,
									  java.util.function.Supplier<GenerateResultEnv> figs) {
	void doResult(final @NotNull WorkManager wm) {
		// TODO find GenerateResultEnv and centralise them
		final WorkList           wl             = new WorkList();
		final GenerateFiles      generateFiles1 = generateModuleRequest.getGenerateFiles(figs);
		final Old_GenerateResult gr             = gr();

		generateFiles1.finishUp(gr, wm, wl);

		wm.addJobs(wl);
		wm.drain();

		gr.additional(generateResult);
	}

	private Old_GenerateResult gr() {
		return generateNodesIntoSink._env().gr();
	}
}

package tripleo.elijah.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.Sub_GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNode;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNode1;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.List;
import java.util.function.Supplier;

public class GM_GenerateModule {
	private final GM_GenerateModuleRequest gmr;

	public GM_GenerateModule(final GM_GenerateModuleRequest aGmr) {
		gmr = aGmr;
	}

	public GM_GenerateModuleResult getModuleResult(final @NotNull WorkManager wm,
												   final @NotNull GenerateResultSink aResultSink) {
		final OS_Module                         mod                   = gmr.params().getMod();
		final @NotNull GN_GenerateNodesIntoSink generateNodesIntoSink = gmr.generateNodesIntoSink();

		final GenerateResult              gr1 = new Sub_GenerateResult();
		final Supplier<GenerateResultEnv> fgs = () ->new GenerateResultEnv(aResultSink, gr1, wm, new WorkList() /*tautology*/, this);

		final @NotNull GenerateFiles            ggc                   = gmr.getGenerateFiles(fgs);
		final List<ProcessedNode>               lgc                   = generateNodesIntoSink._env().lgc();

		var fileGen = ((GenerateC)ggc).getFileGen();

		for (ProcessedNode processedNode : lgc) {
			final EvaNode evaNode = ((ProcessedNode1) processedNode).getEvaNode();

			if (!(processedNode.matchModule(mod))) continue; // README curious

			if (processedNode.isContainerNode()) {
				processedNode.processContainer(ggc, fileGen);

				processedNode.processConstructors(ggc, fileGen);
				processedNode.processFunctions(ggc, fileGen);
				processedNode.processClassMap(ggc, fileGen);
			} else {
				SimplePrintLoggerToRemoveSoon.println_out_2("2009 " + evaNode.getClass().getName());
			}
		}

		return new GM_GenerateModuleResult(gr1, generateNodesIntoSink, gmr, fgs);
	}

	public GM_GenerateModuleRequest gmr() {
		return gmr;
	}

	public IPipelineAccess pa() {
		return gmr().env().pa();
	}

	public ElLog.Verbosity testSilence() {
		return pa().getCompilationEnclosure().getCompilationAccess().testSilence();
	}

	public DeducePhase getDeducePhase() {
		return pa().getDeducePhase();
	}
}

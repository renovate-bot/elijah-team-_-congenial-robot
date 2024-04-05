package tripleo.elijah_durable_congenial.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNodeImpl;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_durable_congenial.comp.i.IPipelineAccess;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_durable_congenial.stages.gen_generic.Sub_GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl.ProcessedNode;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

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
		final GenerateResult                    gr1                   = new Sub_GenerateResult();
		final Supplier<GenerateResultEnv>       fgs                   = () -> new GenerateResultEnv(aResultSink, gr1, wm, new WorkList() /*tautology*/, this);
		final @NotNull GenerateFiles            ggc                   = gmr.getGenerateFiles(fgs);
		final List<ProcessedNode>               lgc                   = generateNodesIntoSink._env().lgc();
		final GenerateResultEnv                 fileGen               = ggc.getFileGen();

		for (ProcessedNode processedNode : lgc) {
			final EvaNode evaNode = ((ProcessedNodeImpl) processedNode).getEvaNode();

			int res = processedNode.process(mod, ggc, fileGen);
			switch (res) {
			case ProcessedNode.MODULE_MISMATCH -> {
			}
			case ProcessedNode.OK -> {
				// README 24/01/03 ??
			}
			case ProcessedNode.OTHER -> {
				throw new UnintendedUseException("24/01/03 ??");
			}
			default -> SimplePrintLoggerToRemoveSoon.println_out_2("2009 " + evaNode.getClass().getName());
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

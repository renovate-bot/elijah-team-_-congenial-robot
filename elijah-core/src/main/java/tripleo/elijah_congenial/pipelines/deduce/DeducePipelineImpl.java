package tripleo.elijah_congenial.pipelines.deduce;

import com.google.common.base.Preconditions;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

public class DeducePipelineImpl {
	public DeducePipelineImpl(final IPipelineAccess aPipelineAccess) {
	}

	public void run(final CR_State aSt, final CB_Output aOutput) {
		logProgress("***** Hit DeducePipeline #run");

		final CompilationEnclosure                   ce            = aSt.ce();
		final IPipelineAccess                        pa            = ce.getPipelineAccess();
		final LivingRepo                             world         = ce.ca2().world();
		final PipelineLogic                          pipelineLogic = ce.getPipelineLogic();
		final DeducePhase                            deducePhase   = pipelineLogic.dp;
		final PipelineLogic.ModuleCompletableProcess mcp           = pipelineLogic._mcp();

		Preconditions.checkNotNull(pa);
		Preconditions.checkNotNull(ce);
		Preconditions.checkNotNull(pipelineLogic);
		Preconditions.checkNotNull(deducePhase);
		Preconditions.checkNotNull(mcp);

		ce.getCompilation().eachModule(m -> {
			pipelineLogic.addModule(m);

			final WorldModule worldModule = ce.ca2().createWorldModule(m);
			world.addModule2(worldModule);
		});

		mcp.start();

		world.addModuleProcess(mcp);

		mcp.preComplete();
		mcp.complete();

		deducePhase.country().sendClasses(pa::setNodeList);
	}

	protected void logProgress(final String g) {
		SimplePrintLoggerToRemoveSoon.println_err_2(g);
	}
}

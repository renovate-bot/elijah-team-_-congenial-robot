package tripleo.elijah_durable_congenial.comp;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.comp.i.IPipelineAccess;
import tripleo.elijah_durable_congenial.comp.internal.CB_Output;
import tripleo.elijah_durable_congenial.comp.internal.CR_State;
import tripleo.elijah_durable_congenial.comp.signal.DeducePipeline_finishedSignal;
import tripleo.elijah_durable_congenial.comp.signal.SimpleSignalListener;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.world.i.LivingRepo;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

/**
 * Created 8/21/21 10:10 PM
 */
public class DeducePipeline implements PipelineMember {
	public DeducePipeline(final IPipelineAccess aPipelineAccess) {
	}

	// NOTES 11/10
	//  1. #createWorldModule is only created here
	//    - this is contrary to other branches where there are more than one location
	//  2. mcp is a bit involved
	//  3. We loop modules
	@Override
	public void run(final @NotNull CR_State aSt, final CB_Output aOutput) {
		logProgress("***** Hit DeducePipeline #run");

		final CompilationEnclosure                   ce            = aSt.ca().getCompilation().getCompilationEnclosure();
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

		pa.getCompilation().eachModule(m -> {
			pipelineLogic.addModule(m);

			final WorldModule worldModule = ce.ca2().createWorldModule(m);
			world.addModule2(worldModule);
		});

		mcp.start();

		world.addModuleProcess(mcp);

		mcp.preComplete();
		mcp.complete();

		ce.getCompilation().signalSimpleListener(DeducePipeline_finishedSignal.INSTANCE.getListenerCode(), new SimpleSignalListener(){
			@Override
			public void run() {
				deducePhase.country().sendClasses(pa::setNodeList);
			}
		});

		ce.getCompilation().signalSimple(DeducePipeline_finishedSignal.INSTANCE);
	}

	protected void logProgress(final String g) {
		SimplePrintLoggerToRemoveSoon.println_err_2(g);
	}
}

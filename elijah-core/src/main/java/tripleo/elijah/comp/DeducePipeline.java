/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;

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

		final CompilationEnclosure                   ce                   = aSt.ca().getCompilation().getCompilationEnclosure();
		final IPipelineAccess                        pa                   = ce.getPipelineAccess();
		final LivingRepo                             world                = ce.ca2().world();
		final PipelineLogic                          pipelineLogic        = ce.getPipelineLogic();
		final DeducePhase                            deducePhase          = pipelineLogic.dp;
		final PipelineLogic.ModuleCompletableProcess mcp                  = pipelineLogic._mcp();

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

		deducePhase.country().sendClasses(pa::setNodeList);
	}

	protected void logProgress(final String g) {
		SimplePrintLoggerToRemoveSoon.println_err_2(g);
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

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
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

/**
 * Created 8/21/21 10:10 PM
 */
public class DeducePipeline implements PipelineMember {
	public DeducePipeline(final IPipelineAccess aPipelineAccess) {
	}

	protected void logProgress(final String g) {
		tripleo.elijah.util.Stupidity.println_err_2(g);
	}

	@Override
	public void run(final @NotNull CR_State aSt, final CB_Output aOutput) {
		logProgress("***** Hit DeducePipeline #run");

		final IPipelineAccess      pa                   = aSt.ca().getCompilation().getCompilationEnclosure().getPipelineAccess();
		final Compilation          c                    = pa.getCompilation();
		final CompilationEnclosure compilationEnclosure = c.getCompilationEnclosure();
		final PipelineLogic        pipelineLogic        = compilationEnclosure.getPipelineLogic();

		Preconditions.checkNotNull(pa);
		Preconditions.checkNotNull(c);
		Preconditions.checkNotNull(compilationEnclosure);
		Preconditions.checkNotNull(pipelineLogic);

		for (final OS_Module m : c.modules()) {
			pipelineLogic.addModule(m);
		}

		final PipelineLogic.ModuleCompletableProcess mcp = pipelineLogic._mcp();

		Preconditions.checkNotNull(mcp);

		mcp.start();

		final CompilationEnclosure ce    = pipelineLogic._pa().getCompilationEnclosure();
		final LivingRepo           world = ce.getCompilation().world();
		world.addModuleProcess(mcp);

		for (final OS_Module mod : pipelineLogic.mods().getMods()) {
			final WorldModule mod1    = c.con().createWorldModule(mod);
			world.addModule2(mod1);
		}

		mcp.preComplete();
		mcp.complete();

		final DeducePhase deducePhase = pipelineLogic.dp;

		Preconditions.checkNotNull(deducePhase);

		deducePhase.country().sendClasses(pa::setNodeList);
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

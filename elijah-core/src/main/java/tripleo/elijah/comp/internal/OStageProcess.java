package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Pipeline;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationAccess2;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.i.RuntimeProcess;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OStageProcess implements RuntimeProcess {
	private final ICompilationAccess ca;
	private final ICompilationAccess2 ca2;

	public OStageProcess(final ICompilationAccess aCa, final @NotNull ProcessRecord aPr) {
		ca  = aCa;
		ca2 = aCa.getCompilation().getCompilationAccess2();

		ca.getCompilation().getCompilationEnclosure().getAccessBusPromise()
				.then(iab -> {
					iab.subscribePipelineLogic(pl -> {
						// FIXME 24/01/03:
						//  we need pl, but do we need ab?
						final Compilation comp = ca.getCompilation();

						// also, spi +/- reactivedim:modules_ready(ready_for_modules...) +/- LCM??

						comp.eachModule(pl::addModule);
					});
				});
	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() {
		ca2.mal_ReadEval("(def! EvaPipeline 'native)");

		ca2.mal_ReadEval("(add-pipeline 'HooliganPipeline)");
		ca2.mal_ReadEval("(add-pipeline 'EvaPipeline)");


		ca2.mal_ReadEval("(add-pipeline 'DeducePipeline)"); // FIXME note moved from ...

		ca2.mal_ReadEval("(add-pipeline 'WritePipeline)");
		//ca2.mal_ReadEval("(add-pipeline 'WriteMesonPipeline)");
		ca2.mal_ReadEval("(add-pipeline 'WriteMakefilePipeline)");
		ca2.mal_ReadEval("(add-pipeline 'WriteOutputTreePipeline)"); // TODO add error checking
	}

	@Override
	public void run(final @NotNull Compilation aCompilation, final CR_State st, final CB_Output output) {
		final Pipeline ps = aCompilation.getCompilationEnclosure().getCompilationAccess().internal_pipelines();

		try {
			ps.run(st, output);
		} catch (Exception ex) {
			Logger.getLogger(OStageProcess.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}


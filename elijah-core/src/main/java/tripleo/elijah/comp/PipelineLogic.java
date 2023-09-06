/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.Provenance;
import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.comp.notation.GN_PL_Run2_Env;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.gen_fn.GeneratePhase;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created 12/30/20 2:14 AM
 */
public class PipelineLogic {
	public final          List<ElLog>                                                              elLogs         = new LinkedList<>();
	public final @NotNull DeducePhase                                                              dp;
	public final @NotNull GeneratePhase                                                            generatePhase;
	private final         List<OS_Module>                                                          __mods_BACKING = new ArrayList<>();
	private final         EIT_ModuleList                                                           mods           = new EIT_ModuleList(__mods_BACKING);
	private final         Map<OS_Module, DeferredObject<DeducePhase.GeneratedClasses, Void, Void>> modMap         = new HashMap<>();
	private final         IPipelineAccess                                                          pa;
	private final         ElLog.Verbosity                                                          verbosity;

	public PipelineLogic(final IPipelineAccess aPa, final @NotNull ICompilationAccess ca) {
		pa = aPa;

		// TODO annotation time, or use clj
		pa.install_notate(Provenance.PipelineLogic__nextModule, GN_PL_Run2.class, GN_PL_Run2_Env.class);

		ca.setPipelineLogic(this);
		verbosity     = ca.testSilence();
		generatePhase = new GeneratePhase(verbosity, pa, this);
		dp            = new DeducePhase(ca, pa, this);

		pa.getCompilationEnclosure().addModuleListener(new CompilationEnclosure.ModuleListener() {
			@Override
			public void listen(final WorldModule module) {
				final OS_Module         mod = module.module();
				final GenerateFunctions gfm = getGenerateFunctions(mod);

				gfm.generateFromEntryPoints(module.rq());

				final DeducePhase.@NotNull GeneratedClasses lgc = dp.generatedClasses;

				modMap.get(mod).resolve(lgc);
			}
		});
	}

	@NotNull
	public GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod) {
		return generatePhase.getGenerateFunctions(mod);
	}

	public Promise<DeducePhase.GeneratedClasses, Void, Void> handle(final GN_PL_Run2.@NotNull GenerateFunctionsRequest rq) {
		final OS_Module          mod         = rq.mod();
		final DefaultWorldModule worldModule = new DefaultWorldModule(mod, rq);

		final DeferredObject<DeducePhase.GeneratedClasses, Void, Void> p = new DeferredObject<>();
		modMap.put(mod, p);

		pa.getCompilationEnclosure().addModule(worldModule);

		return p.promise();
	}

	public void addLog(ElLog aLog) {
		elLogs.add(aLog);
	}

	public void addModule(OS_Module m) {
		mods.add(m);
	}

	public ElLog.Verbosity getVerbosity() {
		return verbosity;
	}

	public @NotNull EIT_ModuleList mods() {
		return mods;
	}

	public final ModuleCompletableProcess mcp = new ModuleCompletableProcess();

	public final class ModuleCompletableProcess implements CompletableProcess<OS_Module> {

		@Override
		public void add(final @NotNull OS_Module mod) {
			//System.err.printf("7070 %s %d%n", mod.getFileName(), mod.entryPoints.size());

			final CompilationEnclosure  ce            = pa.getCompilationEnclosure();
			final Consumer<WorldModule> worldConsumer = ce::noteAccept; // FIXME not data...
			final GN_PL_Run2_Env        pl_run2       = new GN_PL_Run2_Env(PipelineLogic.this, mod, ce, worldConsumer);

			pa.notate(Provenance.PipelineLogic__nextModule, pl_run2);
		}

		@Override
		public void complete() {
			dp.finish();
		}

		@Override
		public void error(final Diagnostic d) {

		}

		@Override
		public void preComplete() {

		}

		@Override
		public void start() {

		}
	}
}

//
//
//

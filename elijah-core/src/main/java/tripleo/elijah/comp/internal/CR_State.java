/*  -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.DeducePipeline;
import tripleo.elijah.comp.EvaPipeline;
import tripleo.elijah.comp.HooliganPipeline;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.PipelineMember;
import tripleo.elijah.comp.WriteMakefilePipeline;
import tripleo.elijah.comp.WriteMesonPipeline;
import tripleo.elijah.comp.WriteOutputTreePipeline;
import tripleo.elijah.comp.WritePipeline;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah.comp.notation.GN_Notable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.logging.ElLog;

public class CR_State {
	public CB_Action     cur;
	public ProcessRecord pr;
	public RuntimeProcesses          rt;
	public boolean                   started;
	ICompilationAccess ca;
	private CompilationRunner compilationRunner;

	@Contract(pure = true)
	public CR_State(ICompilationAccess aCa) {
		ca = aCa;
		ca.getCompilation().set_pa(new ProcessRecord_PipelineAccess()); // FIXME 05/28
		pr = new ProcessRecordImpl(ca);
	}

	public ICompilationAccess ca() {
		return ca;
	}

	public CompilationRunner runner() {
		return compilationRunner;
	}

	public void setRunner(CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;
	}

	public interface PipelinePlugin {
		PipelineMember instance(final @NotNull AccessBus ab0);

		String name();
	}

	public static class DeducePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new DeducePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "DeducePipeline";
		}
	}

	public static class EvaPipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new EvaPipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "EvaPipeline";
		}
	}

	public static class HooliganPipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new HooliganPipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "HooliganPipeline";
		}
	}

	private static class ProcessRecordImpl implements ProcessRecord {
		//private final DeducePipeline                             dpl;
		private final @NotNull ICompilationAccess ca;
		private final          IPipelineAccess    pa;
		private final @NotNull PipelineLogic      pipelineLogic;
		private                AccessBus          ab;

		public ProcessRecordImpl(final @NotNull ICompilationAccess ca0) {
			ca = ca0;

			//ca.getCompilation().getCompilationEnclosure().getAccessBusPromise()
			//		.then(iab->ab=iab);
			ca.getCompilation().getCompilationEnclosure().getAccessBusPromise().then((final @NotNull AccessBus iab) -> {
				ab = iab;
			});

			pa = ca.getCompilation().get_pa();

			pipelineLogic = new PipelineLogic(pa, ca);
		}

		@Contract(pure = true)
		@Override
		public AccessBus ab() {
			return ab;
		}

		@Contract(pure = true)
		@Override
		public ICompilationAccess ca() {
			return ca;
		}

		@Contract(pure = true)
		@Override
		public IPipelineAccess pa() {
			return pa;
		}

		@Contract(pure = true)
		@Override
		public PipelineLogic pipelineLogic() {
			return pipelineLogic;
		}

		@Override
		public void writeLogs() {
			ca.getCompilation().cfg().stage.writeLogs(ca);
		}
	}

	public static class WriteMakefilePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WriteMakefilePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WriteMakefilePipeline";
		}
	}

	public static class WriteMesonPipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WriteMesonPipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WriteMesonPipeline";
		}
	}

	public static class WriteOutputTreePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WriteOutputTreePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WriteOutputTreePipeline";
		}
	}

	public static class WritePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull AccessBus ab0) {
			return new WritePipeline(ab0.getPipelineAccess());
		}

		@Override
		public @NotNull String name() {
			return "WritePipeline";
		}
	}

	class ProcessRecord_PipelineAccess implements IPipelineAccess, EventualRegister {
		private final @NotNull List<EvaNode>                                         _l_classes       = new ArrayList<>();
		private final @NotNull List<EvaClass>                                        activeClasses    = new ArrayList<>();
		private final @NotNull List<BaseEvaFunction>                                 activeFunctions  = new ArrayList<>();
		private final @NotNull List<EvaNamespace>                                    activeNamespaces = new ArrayList<>();
		private final @NotNull Eventual<PipelineLogic>                               _p_pipelineLogic = new Eventual<>();
		private final @NotNull Eventual<EvaPipeline>                                 _p_EvaPipeline   = new Eventual<>();
		private final          Map<OS_Module, DeferredObject<GenerateC, Void, Void>> gc2m_map         = new HashMap<>();
		private final @NotNull Map<Provenance, Pair<Class, Class>>                   installs         = new HashMap<>();
		private final          DeferredObject<List<EvaNode>, Void, Void>             nodeListPromise  = new DeferredObject<>();
		private final          List<NG_OutputItem>                                   outputs          = new ArrayList<NG_OutputItem>();
		private final          List<OS_Module>                                       __mods_BACKING   = new ArrayList<>();
		private final          EIT_ModuleList                                        mods             = new EIT_ModuleList(__mods_BACKING);
		private                AccessBus                                             _ab;
		private                WritePipeline                                         _wpl;
		private                GenerateResultSink                                    grs;
		private                List<CompilerInput>                                   inp;

		public ProcessRecord_PipelineAccess() {
			_p_EvaPipeline.register(this);
			_p_pipelineLogic.register(this);
		}

		@Override
		public void _setAccessBus(final AccessBus ab) {
			_ab = ab;
		}

		@Override
		public void addFunctionStatement(final EvaPipeline.FunctionStatement aFunctionStatement) {
			_p_EvaPipeline.then(gp -> {
				gp.addFunctionStatement(aFunctionStatement);
			});
		}

		@Override
		public void addLog(final ElLog aLOG) {
			getCompilationEnclosure().getPipelineLogic().addLog(aLOG);
		}

		@Override
		public void addOutput(final NG_OutputItem aOutput) {
			this.outputs.add(aOutput);
		}

		@Override
		public AccessBus getAccessBus() {
			return _ab;
		}

		@Override
		public Compilation getCompilation() {
			return ca.getCompilation();
		}

		@Override
		public CompilationEnclosure getCompilationEnclosure() {
			return getCompilation().getCompilationEnclosure();
		}

		@Override
		public GenerateResultSink getGenerateResultSink() {
			return grs;
		}

		@Override
		public @NotNull Eventual<PipelineLogic> getPipelineLogicPromise() {
			return _p_pipelineLogic;
		}

		@Override
		public @NotNull Eventual<EvaPipeline> getEvaPipelinePromise() {
			return _p_EvaPipeline;
		}

		@Override
		public ProcessRecord getProcessRecord() {
			return pr;
		}

		@Override
		public WritePipeline getWitePipeline() {
			return _wpl;
		}

		@Override
		public void notate(final Provenance provenance, final @NotNull GN_Notable aNotable) {
			var cb = getCompilationEnclosure().getCompilationBus();

			// FIXME 07/01 this doesn't work, why??
			//  also keep in mind `provenance'
			final NotableAction notableAction = new NotableAction(aNotable);

			cb.add(notableAction);

			// 09/01 aNotable.run();
			notableAction._actual_run();
		}

		@Override
		public PipelineLogic pipelineLogic() {
			return getProcessRecord().pipelineLogic();
		}

		@Override
		public void registerNodeList(final DoneCallback<List<EvaNode>> done) {
			nodeListPromise.then(done);
		}

		//@Override
		//public @NotNull List<NG_OutputItem> getOutputs() {
		//	return outputs;
		//}

		@Override
		public void setEvaPipeline(final @NotNull EvaPipeline agp) {
			_p_EvaPipeline.resolve(agp);
		}

		@Override
		public void setGenerateResultSink(final GenerateResultSink aGenerateResultSink) {
			grs = aGenerateResultSink;
		}

		@Override
		public void setNodeList(final @NotNull List<EvaNode> aEvaNodeList) {
			nodeListPromise.resolve(aEvaNodeList);
		}

		@Override
		public void setWritePipeline(final WritePipeline aWritePipeline) {
			_wpl = aWritePipeline;
		}

		@Override
		public void activeFunction(final BaseEvaFunction aEvaFunction) {
			activeFunctions.add(aEvaFunction);
		}

		@Override
		public void activeClass(final EvaClass aEvaClass) {
			activeClasses.add(aEvaClass);
		}

		@Override
		public void activeNamespace(final EvaNamespace aEvaNamespace) {
			activeNamespaces.add(aEvaNamespace);
		}

		@Override
		public @NotNull List<EvaNamespace> getActiveNamespaces() {
			return activeNamespaces;
		}

		@Override
		public @NotNull List<BaseEvaFunction> getActiveFunctions() {
			return activeFunctions;
		}

		@Override
		public @NotNull List<EvaClass> getActiveClasses() {
			return activeClasses;
		}

		@Override
		public void _send_GeneratedClass(final EvaNode aClass) {
			_l_classes.add(aClass);
		}

		@Override
		public void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
			final DeferredObject<GenerateC, Void, Void> v = gc2m_map.get(mod);
			assert v != null;
			v.then(ggc -> cb.accept(ggc));
		}

		@Override
		public void resolveWaitGenC(final OS_Module mod, final GenerateC gc) {
			DeferredObject<GenerateC, Void, Void> gcp = new DeferredObject<>();
			gcp.resolve(gc);
			gc2m_map.put(mod, gcp);
		}

		@Override
		public void install_notate(final Provenance aProvenance, final Class<? extends GN_Notable> aRunClass, final Class<? extends GN_Env> aEnvClass) {
			installs.put(aProvenance, Pair.of(aRunClass, aEnvClass));
		}

		@Override
		public void notate(final Provenance aProvenance, final GN_Env aGNEnv) {
			var y = installs.get(aProvenance);
			//System.err.println("210 "+y);

			Class<?> x = y.getLeft();
			//var z = y.getRight();

			try {
				var inst = x.getMethod("getFactoryEnv", GN_Env.class);

				var notable1 = inst.invoke(null, aGNEnv);

				if (notable1 instanceof @NotNull GN_Notable notable) {
					final NotableAction notableAction = new NotableAction(notable);

					//cb.add(notableAction);

					notableAction._actual_run();

					//System.err.println("227 "+inst);
				}
			} catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public void resolvePipelinePromise(final PipelineLogic aPipelineLogic) {
			this._ab.resolvePipelineLogic(aPipelineLogic);
		}

		@Override
		public DeducePhase getDeducePhase() {
			return getCompilationEnclosure().getPipelineLogic().dp;
		}

		@Override
		public EIT_ModuleList getModuleList() {
			return mods;
		}

		@Override
		public void _ModuleList_add(final OS_Module aModule) {
			mods.add(aModule);
		}

		@Override
		public <P> void register(final Eventual<P> e) {
			_l_eventuals.add(e);
		}

		private final List<Eventual<?>> _l_eventuals = new ArrayList<>();
		@Override
		public void checkFinishEventuals() {
			for (Eventual<?> eventual : _l_eventuals) {
				if (eventual.isPending()) {
					System.err.println("-- [PipelineAccess] eventual did not resolve "+eventual.description());
				}
			}
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

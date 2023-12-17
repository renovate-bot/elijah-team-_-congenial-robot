package tripleo.elijah.comp.i;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.internal.CompilerDriver;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.Reactive;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.pre_world.Mirror_EntryPoint;
import tripleo.elijah.stages.gen_fn.IClassGenerator;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.world.i.WorldModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompilationEnclosure {
	public final  DeferredObject<IPipelineAccess, Void, Void> pipelineAccessPromise = new DeferredObject<>();
	private final CB_Output _cbOutput = new CB_Output();
	private final Compilation compilation;
	private final DeferredObject<AccessBus, Void, Void>       accessBusPromise      = new DeferredObject<>();
	private final Map<OS_Module, ModuleThing> moduleThings = new HashMap<>();
	private final Subject<ReactiveDimension> dimensionSubject   = ReplaySubject.<ReactiveDimension>create();
	private final Subject<Reactivable>       reactivableSubject = ReplaySubject.<Reactivable>create();
	private final List<ModuleListener> _moduleListeners = new ArrayList<>();
	Observer<ReactiveDimension> dimensionObserver = new Observer<ReactiveDimension>() {
		@Override
		public void onSubscribe(@NonNull final Disposable d) {

		}

		@Override
		public void onNext(@NonNull final ReactiveDimension aReactiveDimension) {
			// aReactiveDimension.observe();
			throw new IllegalStateException("Error");
		}

		@Override
		public void onError(@NonNull final Throwable e) {

		}

		@Override
		public void onComplete() {

		}
	};
	Observer<Reactivable> reactivableObserver = new Observer<Reactivable>() {

		@Override
		public void onSubscribe(@NonNull final Disposable d) {

		}

		@Override
		public void onNext(@NonNull final Reactivable aReactivable) {
//			ReplaySubject
			throw new IllegalStateException("Error");
		}

		@Override
		public void onError(@NonNull final Throwable e) {

		}

		@Override
		public void onComplete() {

		}
	};
	private AccessBus           ab;
	private ICompilationAccess  ca;
	private ICompilationBus     compilationBus;
	private CompilationRunner   compilationRunner;
	private CompilerDriver      compilerDriver;
	private List<CompilerInput> inp;
	private IPipelineAccess     pa;
	private PipelineLogic       pipelineLogic;

	public CompilationEnclosure(final Compilation aCompilation) {
		compilation = aCompilation;

		getPipelineAccessPromise().then(pa -> {
			ab = new AccessBus(getCompilation(), pa);

			accessBusPromise.resolve(ab);

			ab.addPipelinePlugin(new CR_State.HooliganPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.EvaPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.DeducePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WritePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteMakefilePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteMesonPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteOutputTreePipelinePlugin());

			pa._setAccessBus(ab);

			this.pa = pa;
		});
	//
	//	compilation.world().addModuleProcess(new CompletableProcess<WorldModule>() {
	//		@Override
	//		public void add(final WorldModule item) {
	//			// TODO Reactive pattern (aka something ala ReplaySubject)
	//			for (final ModuleListener moduleListener : _moduleListeners) {
	//				moduleListener.listen(item);
	//			}
	//		}
	//
	//		@Override
	//		public void complete() {
	//			// TODO Reactive pattern (aka something ala ReplaySubject)
	//			for (final ModuleListener moduleListener : _moduleListeners) {
	//				moduleListener.close();
	//			}
	//		}
	//
	//		@Override
	//		public void error(final Diagnostic d) {
	//
	//		}
	//
	//		@Override
	//		public void preComplete() {
	//
	//		}
	//
	//		@Override
	//		public void start() {
	//
	//		}
	//	});
	}

	@Contract(pure = true)
	public @NotNull Promise<IPipelineAccess, Void, Void> getPipelineAccessPromise() {
		return pipelineAccessPromise;
	}

	@Contract(pure = true)
	public Compilation getCompilation() {
		return compilation;
	}

	public void addReactive(@NotNull Reactive r) {
		dimensionSubject.subscribe(new Observer<ReactiveDimension>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(@NonNull ReactiveDimension dim) {
				r.join(dim);
			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});
	}

	public void addReactiveDimension(final ReactiveDimension aReactiveDimension) {
		dimensionSubject.onNext(aReactiveDimension);

		reactivableSubject.subscribe(new Observer<Reactivable>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(@NonNull final @NotNull Reactivable aReactivable) {
				addReactive(aReactivable);
			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});

//		aReactiveDimension.setReactiveSink(addReactive);
	}

	public void addReactive(@NotNull Reactivable r) {
		int y = 2;
		// reactivableObserver.onNext(r);
		reactivableSubject.onNext(r);

		// reactivableObserver.
		dimensionSubject.subscribe(new Observer<ReactiveDimension>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(final ReactiveDimension aReactiveDimension) {
				// r.join(aReactiveDimension);
				r.respondTo(aReactiveDimension);
			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});
	}

	public @NotNull Promise<AccessBus, Void, Void> getAccessBusPromise() {
		return accessBusPromise;
	}

	@Contract(pure = true)
	public CB_Output getCB_Output() {
		return this._cbOutput;
	}

	@Contract(pure = true)
	public @NotNull ICompilationAccess getCompilationAccess() {
		return ca;
	}

	public void setCompilationAccess(@NotNull ICompilationAccess aca) {
		ca = aca;
	}

	//@Contract(pure = true) //??
	public CompilationClosure getCompilationClosure() {
		return this.getCompilation().getCompilationClosure();
	}

	@Contract(pure = true)
	public CompilerDriver getCompilationDriver() {
		return getCompilationBus().getCompilationDriver();
	}

	@Contract(pure = true)
	public ICompilationBus getCompilationBus() {
		return compilationBus;
	}

	public void setCompilationBus(final ICompilationBus aCompilationBus) {
		compilationBus = aCompilationBus;
	}

	@Contract(pure = true)
	public CompilationRunner getCompilationRunner() {
		return compilationRunner;
	}

	public void setCompilationRunner(final CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;
	}

	@Contract(pure = true)
	public CompilerDriver getCompilerDriver() {
		return compilerDriver;
	}

	public void setCompilerDriver(final CompilerDriver aCompilerDriver) {
		compilerDriver = aCompilerDriver;
	}

	@Contract(pure = true)
	public List<CompilerInput> getCompilerInput() {
		return inp;
	}

	public void setCompilerInput(final List<CompilerInput> aInputs) {
		inp = aInputs;
	}

	@Contract(pure = true)
	public IPipelineAccess getPipelineAccess() {
		return pa;
	}

	@Contract(pure = true)
	public PipelineLogic getPipelineLogic() {
		return pipelineLogic;
	}

	public void setPipelineLogic(final PipelineLogic aPipelineLogic) {
		pipelineLogic = aPipelineLogic;

		getPipelineAccessPromise().then(pa->pa.resolvePipelinePromise(aPipelineLogic));
	}

	private void _resolvePipelineAccess(final PipelineLogic aPipelineLogic) {
	}

	public ModuleThing getModuleThing(final OS_Module aMod) {
		if (moduleThings.containsKey(aMod)) {
			return moduleThings.get(aMod);
		}
		return addModuleThing(aMod);
	}

	public @NotNull ModuleThing addModuleThing(final OS_Module aMod) {
		var mt = new ModuleThing(aMod);
		moduleThings.put(aMod, mt);
		return mt;
	}

	public void noteAccept(final @NotNull WorldModule aWorldModule) {
		var mod = aWorldModule.module();
		var aMt = aWorldModule.rq().mt();
		// System.err.println(mod);
		// System.err.println(aMt);
	}

	public void reactiveJoin(final Reactive aReactive) {
		// throw new IllegalStateException("Error");

		// aReactive.join();
		System.err.println("reactiveJoin "+ aReactive.toString());
	}

	public void addModuleListener(final ModuleListener aModuleListener) {
		_moduleListeners.add(aModuleListener);
	}

	public void addModule(final WorldModule aWorldModule) {
		// TODO Reactive pattern (aka something ala ReplaySubject)
		for (final ModuleListener moduleListener : _moduleListeners) {
			moduleListener.listen(aWorldModule);
		}
	}

	public void addEntryPoint(final @NotNull Mirror_EntryPoint aMirrorEntryPoint, final IClassGenerator dcg) {
		aMirrorEntryPoint.generate(dcg);
	}

	public void addLog(final ElLog aLOG) {
		var ce = this;
		ce.getAccessBusPromise()
				.then(ab -> {
					ab.subscribePipelineLogic(pl -> pl.addLog(aLOG));
				});
	}

	public ICompilationAccess2 ca2() {
		return compilation.getCompilationAccess2();
	}

	public interface ModuleListener {
		void listen(WorldModule module);

		void close();
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

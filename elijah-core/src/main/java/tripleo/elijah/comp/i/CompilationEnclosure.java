package tripleo.elijah.comp.i;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

import lombok.Setter;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.PipelineLogic;

import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.internal.CompilerDriver;
import tripleo.elijah.comp.internal.MalBulge;
import tripleo.elijah.comp.internal.Provenance;
import tripleo.elijah.comp.internal.__Plugins;

import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.comp.nextgen.i.CE_Path;

import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.ER_Node;

import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;

import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.Reactive;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;

import tripleo.elijah.nextgen.spi.SPI_Loggable;
import tripleo.elijah.nextgen.spi.SPI_ReactiveDimension;

import tripleo.elijah.pre_world.Mirror_EntryPoint;

import tripleo.elijah.stages.gen_fn.IClassGenerator;
import tripleo.elijah_congenial.pipelines.NextgenFactory;

import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.world.i.WorldModule;

import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stages.logging.LogEntry;
import tripleo.elijah.comp.notation.GN_WriteLogs;


import java.nio.file.Path;
import java.util.*;

import static tripleo.elijah.util.Helpers.List_of;

public class CompilationEnclosure {
	public final          List<ElLog>                                            elLogs                  = new LinkedList<>();

	
	public final  DeferredObject<IPipelineAccess, Void, Void> pipelineAccessPromise = new DeferredObject<>();
	private final CB_Output                                   _cbOutput             = new CB_Output();
	private final Compilation                                 compilation;
	private final DeferredObject<AccessBus, Void, Void>       accessBusPromise      = new DeferredObject<>();
	private final Map<OS_Module, ModuleThing>                 moduleThings          = new HashMap<>();
	private final Subject<ReactiveDimension>                  dimensionSubject      = ReplaySubject.<ReactiveDimension>create();
	private final Subject<Reactivable>                        reactivableSubject    = ReplaySubject.<Reactivable>create();
	private final List<ModuleListener>                        _moduleListeners      = new ArrayList<>();
	Observer<ReactiveDimension> dimensionObserver   = new Observer<ReactiveDimension>() {
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
	Observer<Reactivable>       reactivableObserver = new Observer<Reactivable>() {

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
	private ICompilationAccess ca;
	@Setter
	private ICompilationBus   compilationBus;
	@Setter
	private CompilationRunner   compilationRunner;
	@Setter
	private CompilerDriver      compilerDriver;
	private List<CompilerInput> inp;
	private IPipelineAccess     pa;
	private PipelineLogic  pipelineLogic;
	private NextgenFactory _nextgenFactory;


	private MalBulge _mb;

	public CompilationEnclosure(final Compilation aCompilation) {
		compilation = aCompilation;

		final CompilationEnclosure _ce = this;

		getPipelineAccessPromise().then(pa -> {
			ab = new AccessBus(getCompilation(), pa);

			accessBusPromise.resolve(ab);

			ab.addPipelinePlugin(new __Plugins.HooliganPipelinePlugin());
			ab.addPipelinePlugin(new __Plugins.EvaPipelinePlugin());
			ab.addPipelinePlugin(new __Plugins.DeducePipelinePlugin());
			ab.addPipelinePlugin(new __Plugins.WritePipelinePlugin());
			ab.addPipelinePlugin(new __Plugins.WriteMakefilePipelinePlugin());
			ab.addPipelinePlugin(new __Plugins.WriteMesonPipelinePlugin());
			ab.addPipelinePlugin(new __Plugins.WriteOutputTreePipelinePlugin());

			pa._setAccessBus(ab);

			_ce.provide(pa);
		});
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

	@Contract(pure = true)
	public CompilationRunner getCompilationRunner() {
		return compilationRunner;
	}

	//@Contract(pure = true)
	//public CompilerDriver getCompilerDriver() {
	//	return compilerDriver;
	//}

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

		getPipelineAccessPromise().then(pa -> pa.resolvePipelinePromise(aPipelineLogic));
	}

	//private void _resolvePipelineAccess(final PipelineLogic aPipelineLogic) {
	//}

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

		 System.err.println("9998-0323 "+mod);
		 System.err.println("9998-0324 "+aMt);
	}

	public void reactiveJoin(final Reactive aReactive) {
		// throw new IllegalStateException("Error");

		// aReactive.join();
		System.err.println("reactiveJoin " + aReactive.toString());
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

	public void spi(final Object spiable) {
		if (spiable instanceof SPI_Loggable) {
			addLog(((SPI_Loggable) spiable).spiGetLog());
		}
		if (spiable instanceof SPI_ReactiveDimension) {
			addReactiveDimension(((SPI_ReactiveDimension) spiable).spiGetReactiveDimension());
		}
	}

	//public void spi(final Object spiable, final Object cacheUnderKey) {
	//	throw new NotImplementedException("12/31/23");
	//}

	public void addLog(final ElLog aLOG) {
		elLogs.add(aLOG);
	}

	public ICompilationAccess2 ca2() {
		return compilation.getCompilationAccess2();
	}

	public NextgenFactory nextgenFactory() {
		if (_nextgenFactory == null) {
			_nextgenFactory = new NextgenFactory() {
				@Override
				public ER_Node createERNode(final CP_Path aPath, final EG_Statement aSeq) {
					/**
					 * See {@link tripleo.elijah.comp.nextgen.i.CompOutput#writeToPath(CE_Path, EG_Statement)}
					 */
					return new ER_Node() {
						@Override
						public @NotNull String toString() {
							return "17 ER_Node " + aPath.toFile();
						}

						@Override
						public Path getPath() {
							final Path pp = aPath.getPath();
							return pp;
						}

						@Override
						public EG_Statement getStatement() {
							return aSeq;
						}
					};
				}
			};
		}
		return _nextgenFactory;
	}

	public void __addLogs(final @NotNull List<EOT_OutputFile> l) {
		final List<ElLog>          logs                 = elLogs;
		final String               s1                   = logs.get(0).getFileName();

		for (final ElLog log : logs) {
			final List<EG_Statement> stmts = new ArrayList<>();

			if (log.getEntries().isEmpty()) continue; // FIXME 24j1 Prelude.elijjah "fails" here

			for (final LogEntry entry : log.getEntries()) {
				final String logentry = String.format("[%s] [%tD %tT] %s %s", s1, entry.time, entry.time, entry.level, entry.message);
				stmts.add(new EG_SingleStatement(logentry + "\n"));
			}

			final EG_SequenceStatement seq      = new EG_SequenceStatement(new EG_Naming("wot.log.seq"), stmts); // <- ??
			final String               fileName = log.getFileName().replace("/", "~~");
			final EOT_OutputFile       off      = new EOT_OutputFile(List_of(), fileName, EOT_OutputType.LOGS, seq);
			l.add(off);
		}
	}

	public void provide(final IPipelineAccess aPipelineAccess) {
		this.pa = aPipelineAccess;
		getCompilation().set_pa(aPipelineAccess);
	}

	public interface ModuleListener {
		void listen(WorldModule module);

		void close();
	}

	public void writeLogs() {
		final IPipelineAccess      pa            = compilation.pa();
		final GN_WriteLogs aNotable = new GN_WriteLogs(ca, elLogs);
		
		pa.notate(Provenance.DefaultCompilationAccess__writeLogs, aNotable);
	}

	public void setMalbulge(MalBulge mb) {
		this._mb = mb;
	}

	public MalBulge getMalBulge() {
		return this._mb;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

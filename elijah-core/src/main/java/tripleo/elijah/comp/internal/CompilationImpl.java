/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.internal;

import io.reactivex.rxjava3.core.Observer;
import io.smallrye.mutiny.Multi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.QualidentImpl;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.stages.deduce.IFunctionMapHook;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah.stages.deduce.fluffy.impl.FluffyCompImpl;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultLivingRepo;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CompilationImpl implements Compilation {
	private final @NotNull FluffyCompImpl                    _fluffyComp;
	private final          CIS                               _cis;
	private final          CompilationConfig                 cfg;
	private final          Map<String, CompilerInstructions> fn2ci;
	private final          List<OS_Module>                   modules;
	private final          USE                               use;
	private final          ErrSink                           errSink;
	private final          int                               _compilationNumber;
	private final          CP_Paths                          paths;
	private final          EIT_InputTree                     _input_tree;
	private final          CompFactory                       _con;
	private final          Finally                           _f;
	private final          LivingRepo                        _repo;
	private final          CompilationEnclosure              compilationEnclosure;
	private @Nullable      EOT_OutputTree                    _output_tree = null;
	private                CompilerInstructions              rootCI;
	private                List<CompilerInput>               _inputs;
	private                IPipelineAccess                   _pa;
	private                IO                                io;

	public CompilationImpl(final ErrSink aErrSink, final IO aIo) {
		this.errSink            = aErrSink;
		this.io                 = aIo;

		this._compilationNumber = new Random().nextInt(Integer.MAX_VALUE);
		fn2ci                   = new HashMap<String, CompilerInstructions>();
		modules                 = new ArrayList<OS_Module>();

		this.paths              = new CP_Paths(this);

		_fluffyComp             = new FluffyCompImpl(this);

		cfg                     = new CompilationConfig();

		_input_tree             = new EIT_InputTree();

		compilationEnclosure    = new CompilationEnclosure(this);
		_repo                   = new DefaultLivingRepo();

		_cis                    = new CIS();
		use                     = new USE(this);

		_con                    = new DefaultCompFactory();
		_f                      = new Finally();
	}

	public @NotNull ICompilationAccess _access() {
		return new DefaultCompilationAccess(this);
	}

	public void testMapHooks(final List<IFunctionMapHook> aMapHooks) {
		//pipelineLogic.dp.
	}

	public CompilationConfig getCfg() {
		return cfg;
	}

	private class DefaultCompFactory implements CompFactory {
		@Override
		public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
			return new EIT_ModuleInput(aModule, CompilationImpl.this);
		}

		@Override
		public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
			Qualident R = new QualidentImpl();
			for (String s : sl) {
				R.append(Helpers.string_to_ident(s));
			}
			return R;
		}

		@Override
		public @NotNull InputRequest createInputRequest(final File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp) {
			return new InputRequest(aFile, aDo_out, aLsp);
		}

		@Override
		public @NotNull WorldModule createWorldModule(final OS_Module m) {
			CompilationEnclosure ce = getCompilationEnclosure();
			final WorldModule    R  = new DefaultWorldModule(m, ce);

			return R;
		}
	}

	@Override
	public @NotNull FluffyComp getFluffy() {
		return _fluffyComp;
	}

	@Override
	public @NotNull EOT_OutputTree getOutputTree() {
		if (_output_tree == null) {
			_output_tree = new EOT_OutputTree();
		}

		assert _output_tree != null;

		return _output_tree;
	}


	@Override
	public CompilerBeginning beginning(final @NotNull CompilationRunner compilationRunner) {
		return new CompilerBeginning(this, getRootCI(), getInputs(), compilationRunner.progressSink, cfg());
	}


	@Override
	public Finally reports() {
		return _f;
	}

	@Override
	public ICompilationAccess2 getCompilationAccess2() {
		return new CompilationAccess2Impl(this);
	}

	@Override
	public Map<String, CompilerInstructions> fn2ci() {
		return getFn2ci();
	}

	@Override
	public USE use() {
		return use;
	}

	@Override
	public CIS _cis() {
		return get_cis();
	}

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}

	@Override
	public void addModule__(final @NotNull OS_Module module, final @NotNull String fn) {
		modules.add(module);
		use.addModule(module, fn);
	}

	@Override
	public @NotNull CompFactory con() {
		return _con;
	}

	@Override
	public void eachModule(final @NotNull Consumer<OS_Module> object) {
		for (OS_Module mod : modules) {
			object.accept(mod);
		}
	}

	@Override
	public int errorCount() {
		return errSink.errorCount();
	}

	@Override
	public void feedInputs(final @NotNull List<CompilerInput> inputs, final @NotNull CompilerController controller) {
		if (inputs.size() == 0) {
			controller.printUsage();
			return;
		}

		{
			//Uni.createFrom().item("hello")
			//		.onItem().transform(item -> item + " mutiny")
			//		.onItem().transform(String::toUpperCase)
			//		.subscribe().with(item -> System.out.println(">> " + item))
			//;

			Multi.createFrom().items(1, 2, 3, 4)
					//Multi.<Integer>createFrom()

/*
							//.emitter(em -> { new MultiEmitter<>() })

							//.deferred(() -> Multi.createFrom()
									.item(Helpers.List_of(1,2,3))
							//)
*/

					.onItem().transform(item -> item)
					.subscribe().with(item -> System.out.println(">> " + item));

			int y = 2;
		}

		_inputs = inputs; // !!
		compilationEnclosure.setCompilerInput(inputs);

		if (controller instanceof DefaultCompilerController) {
			controller._setInputs(this, inputs);
			//} else if (controller instanceof UT_Controller uctl) {
			//	uctl._setInputs(this, inputs);
		}

		controller.processOptions();
		controller.runner();
	}

	@Override
	public void feedCmdLine(final @NotNull List<String> args) throws Exception {
		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput input = new CompilerInput(s);
					if (s.equals(input.getInp())) {
						input.setSourceRoot();
					} else {
						assert false;
					}
					return input;
				})
				.collect(Collectors.toList());

		feedInputs(inputs, new DefaultCompilerController());
	}

	@Override
	public @NotNull CompilationClosure getCompilationClosure() {
		return new CompilationClosure() {

			@Override
			public ErrSink errSink() {
				return errSink;
			}

			@Override
			public @NotNull Compilation getCompilation() {
				return CompilationImpl.this;
			}

			@Override
			public IO io() {
				return io;
			}
		};
	}

	@Override
	public @NotNull List<ClassStatement> findClass(final String aClassName) {
		final List<ClassStatement> l = new ArrayList<ClassStatement>();
		for (final OS_Module module : modules) {
			if (module.hasClass(aClassName)) {
				l.add((ClassStatement) module.findClass(aClassName));
			}
		}
		return l;
	}

	@Override
	public Operation2<WorldModule> findPrelude(final String prelude_name) {
		return use.findPrelude(prelude_name);
	}

	@Override
	public IPipelineAccess get_pa() {
		return _pa;
	}

	@Override
	public List<CompilerInput> getInputs() {
		return _inputs;
	}

	@Override
	public String getCompilationNumberString() {
		return String.format("%08x", _compilationNumber);
	}

	@Override
	public ErrSink getErrSink() {
		return errSink;
	}

	@Override
	public IO getIO() {
		return io;
	}

	@Override
	public void setIO(final IO io) {
		this.io = io;
	}

	@Override
	public OS_Package getPackage(final @NotNull Qualident pkg_name) {
		return _repo.getPackage(pkg_name.toString());
	}

	@Override
	public String getProjectName() {
		return rootCI.getName();
	}

	@Override
	public void hasInstructions(final @NotNull List<CompilerInstructions> cis) {
		assert cis.size() == 1;
		final CompilationEnclosure ce = getCompilationEnclosure();
		assert !ce.getCompilerInput().isEmpty();
		hasInstructions(cis.get(0), pa(), ce);
	}

	public void hasInstructions(final @NotNull CompilerInstructions aRootCI,
								final @NotNull IPipelineAccess pa,
								final CompilationEnclosure ce) {
		//this.signals().hasInstructions()
		//		.signal(this.con().createSignal_hasInstructions(pa, cis)); // this is wrong
		//		.signal(pa, List_of(cis.get(0)));

		rootCI = aRootCI;
		ce.getCompilationRunner().start(rootCI, pa);
	}

	@Override
	public boolean isPackage(final @NotNull String pkg) {
		return _repo.hasPackage(pkg);
	}

	@Override
	public OS_Package makePackage(final Qualident pkg_name) {
		return _repo.makePackage(pkg_name);
	}

	@Override
	public @NotNull ModuleBuilder moduleBuilder() {
		return new ModuleBuilder(this);
	}

	@Override
	public IPipelineAccess pa() {
		assert _pa != null;

		return _pa;
	}

	@Override
	public void set_pa(IPipelineAccess a_pa) {
		_pa = a_pa;

		compilationEnclosure.pipelineAccessPromise.resolve(_pa);
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		_cis.onNext(aci);
	}

	@Override
	public void subscribeCI(final @NotNull Observer<CompilerInstructions> aCio) {
		_cis.subscribe(aCio);
	}

	@Override
	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) {
		use.use(compilerInstructions, do_out);    // NOTE Rust
	}

	@Override
	public LivingRepo world() {
		return _repo;
	}

	@Override
	public LivingRepo livingRepo() {
		return _repo;
	}

	@Override
	public CP_Paths paths() {
		return paths;
	}

	@Override
	public @NotNull EIT_InputTree getInputTree() {
		return _input_tree;
	}

	@Override
	public @NotNull CompilationConfig cfg() {
		return cfg;
	}

	public CIS get_cis() {
		return _cis;
	}


	public Map<String, CompilerInstructions> getFn2ci() {
		return fn2ci;
	}

	public CompilerInstructions getRootCI() {
		return rootCI;
	}

	//public void setRootCI(CompilerInstructions aRootCI) {
	//	rootCI = aRootCI;
	//}
}

//
//
//

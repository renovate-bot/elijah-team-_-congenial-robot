/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.QualidentImpl;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.stages.deduce.IFunctionMapHook;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultLivingRepo;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class Compilation1 implements Compilation {

	public final    CIS                               _cis    = new CIS();
	public final    CompilationConfig                 cfg     = new CompilationConfig();
	public final    Map<String, CompilerInstructions> fn2ci   = new HashMap<String, CompilerInstructions>();
	public final    List<OS_Module>                   modules = new ArrayList<OS_Module>();
	public final    USE                               use     = new USE(this);
	final           ErrSink                           errSink;
	private final   int                               _compilationNumber;
	private final   CompFactory                       _con    = new CompFactory() {
		@Override
		public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
			return new EIT_ModuleInput(aModule, Compilation1.this);
		}

		@Override
		public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
			var R = new QualidentImpl();
			for (String s : sl) {
				R.append(Helpers.string_to_ident(s));
			}
			return R;
		}

		@Override
		public @NotNull InputRequest createInputRequest(final File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp) {
			return new InputRequest(aFile, aDo_out, aLsp);
		}
	};
	public @NotNull LivingRepo                        _repo   = new DefaultLivingRepo();
	public          CompilerInstructions rootCI;
	private List<CompilerInput>  _inputs;
	private IPipelineAccess      _pa;
	private CompilationEnclosure compilationEnclosure = new CompilationEnclosure(this);
	private IO                   io;

	// TODO remove this 04/20
	@Override
	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		getCompilationEnclosure().getCompilationAccess().addFunctionMapHook(aFunctionMapHook);
	}

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}

	@Override
	public void setCompilationEnclosure(final CompilationEnclosure aCompilationEnclosure) {
		compilationEnclosure = aCompilationEnclosure;
	}

	private final CP_Paths paths;

	@Override
	public void addModule__(final @NotNull OS_Module module, final @NotNull String fn) {
		modules.add(module);
		use.addModule(module, fn);
	}

	@Override
	public int compilationNumber() {
		return _compilationNumber;
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
		final CompilerController controller = new DefaultCompilerController();

		if (args.size() == 0) {
			controller.printUsage();
			//System.err.println("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
			return;
		}

		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput input = new CompilerInput(s);
					if (s.equals(input.getInp())) {
						input.setSourceRoot();
					}
					return input;
				})
				.collect(Collectors.toList());

		_inputs = inputs;

		controller._setInputs(this, inputs);
		controller.processOptions();
		controller.runner();
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
				return Compilation1.this;
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
		hasInstructions(cis, pa());
	}

	@Override
	public IPipelineAccess pa() {
		//assert _pa != null;

		return _pa;
	}

	@Override
	public void hasInstructions(final @NotNull List<CompilerInstructions> cis,
								final @NotNull IPipelineAccess pa) {
		assert cis.size() > 0; // FIXME this is corect. below is wrong (allows cis.size()==2)
		//assert cis.size() == 1; // FIXME this is corect. below is wrong (allows cis.size()==2)

		if (cis.size() == 0) {
			// README IDEA misconfiguration
			System.err.println("No CIs found. Current dir is " + new File(".").getAbsolutePath());
			return;
		}

		rootCI = cis.get(0);

		pa.setCompilerInput(pa.getCompilation().getInputs());

		getCompilationEnclosure().getCompilationRunner().start(rootCI, pa);
	}

	@Override
	@Deprecated
	public int instructionCount() {
		return 4; // TODO shim !!!cis.size();
	}

	@Override
	public boolean isPackage(final @NotNull String pkg) {
		return _repo.hasPackage(pkg);
	}

	@Override
	public OS_Package makePackage(final Qualident pkg_name) {
		return _repo.makePackage(pkg_name);
	}

	// endregion

	//
	// region CLASS AND FUNCTION CODES
	//

	@Override
	public @NotNull ModuleBuilder moduleBuilder() {
		return new ModuleBuilder(this);
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		_cis.onNext(aci);
	}

	// endregion

	@Override
	public void set_pa(IPipelineAccess a_pa) {
		_pa = a_pa;

		compilationEnclosure.pipelineAccessPromise.resolve(_pa);
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

	public Compilation1(final ErrSink errSink, final IO io) {
		this.errSink            = errSink;
		this.io                 = io;
		this._compilationNumber = new Random().nextInt(Integer.MAX_VALUE);

		this.paths = new CP_Paths(this);
	}

	@Override
	public CP_Paths paths() {
		return paths;
	}

	private final EIT_InputTree _input_tree = new EIT_InputTree();

	@Override
	public @NotNull EIT_InputTree getInputTree() {
		return _input_tree;
	}

	@Override
	public LivingRepo livingRepo() {
		return _repo;
	}

	@Override
	public @NotNull CompilationConfig cfg() {
		return cfg;
	}

	@Override
	public CompilerBeginning beginning(final @NotNull CompilationRunner aCompilationRunner) {
		return new CompilerBeginning(this, rootCI, _inputs, aCompilationRunner.progressSink, cfg());
	}

}

//
//
//

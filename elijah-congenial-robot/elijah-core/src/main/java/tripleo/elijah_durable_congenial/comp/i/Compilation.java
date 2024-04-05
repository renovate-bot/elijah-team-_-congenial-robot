package tripleo.elijah_durable_congenial.comp.i;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.*;
import tripleo.elijah_durable_congenial.comp.internal.*;
import tripleo.elijah_durable_congenial.comp.nextgen.CP_Paths;
import tripleo.elijah_durable_congenial.comp.signal.SimpleSignalListener;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.lang.i.OS_Package;
import tripleo.elijah_durable_congenial.lang.i.Qualident;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah_durable_congenial.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;
import tripleo.elijah_durable_congenial.world.i.LivingRepo;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface Compilation {
	static ElLog.@NotNull Verbosity gitlabCIVerbosity() {
		final boolean gitlab_ci = isGitlab_ci();
		return gitlab_ci ? ElLog.Verbosity.SILENT : ElLog.Verbosity.VERBOSE;
	}

	static boolean isGitlab_ci() {
		return System.getenv("GITLAB_CI") != null;
	}

	// TODO remove this 04/20
	//void addFunctionMapHook(IFunctionMapHook aFunctionMapHook);

	CompilationEnclosure getCompilationEnclosure();

	//void setCompilationEnclosure(CompilationEnclosure aCompilationEnclosure);

	void addModule__(@NotNull OS_Module module, @NotNull String fn);

	//int compilationNumber();

	CompFactory con();

	void eachModule(Consumer<OS_Module> object);

	int errorCount();

	void feedInputs(@NotNull List<CompilerInput> inputs, CompilerController controller);

	//void fakeFlow(List<CompilerInput> aInputs, CompilationFlow aFlow);

	void feedCmdLine(@NotNull List<String> args) throws Exception;

	CompilationClosure getCompilationClosure();

	List<ClassStatement> findClass(String aClassName);

	Operation2<WorldModule> findPrelude(String prelude_name);

	IPipelineAccess get_pa();

	void addSimpleSignal(String stopBeingLazy, Class<? extends SimpleSignal> aSimpleSignalClass);

	@NotNull FluffyComp getFluffy();

	@Contract(pure = true)
	List<CompilerInput> getInputs();

	String getCompilationNumberString();

	ErrSink getErrSink();

	IO getIO();

	void setIO(IO io);

	OS_Package getPackage(@NotNull Qualident pkg_name);

	String getProjectName();

	void hasInstructions(List<CompilerInstructions> cis);

	boolean isPackage(@NotNull String pkg);

	OS_Package makePackage(Qualident pkg_name);

	ModuleBuilder moduleBuilder();

	IPipelineAccess pa();

	void set_pa(IPipelineAccess a_pa);

	void pushItem(CompilerInstructions aci);

	void subscribeCI(Observer<CompilerInstructions> aCio);

	void use(@NotNull CompilerInstructions compilerInstructions, boolean do_out);

	LivingRepo world();

	LivingRepo livingRepo();

	CP_Paths paths();

	EIT_InputTree getInputTree();

	@NotNull EOT_OutputTree getOutputTree();

	CompilationConfig cfg();

	CompilerBeginning beginning(final CompilationRunner compilationRunner);

	Finally reports();

	ICompilationAccess2 getCompilationAccess2();

	void signalSimple(SimpleSignal aSimpleSignal);

	void signalSimpleListener(String aListenerCode, SimpleSignalListener aSimpleSignalListener);

	Object getSignalResult(SimpleSignal aSimpleSignal);

	void feedSingleFile(String aF);

	void cp_set(CPx aCPx, CompilerInstructionsObserver aCio);

	<T> T get_cp(CPx aCPx);

	<T> Eventual<T> get_cpp(CPx aCPx);

	enum CompilationAlways {
		;

		@NotNull
		public static String defaultPrelude() {
			return "c";
		}

		public enum Tokens {
			;
			public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
			public static final DriverToken COMPILATION_RUNNER_START        = DriverToken.makeToken("COMPILATION_RUNNER_START");
		}
	}

	class CompilationConfig {
		public          boolean do_out;
		public          boolean showTree = false;
		public          boolean silent = false;
		public @NotNull Stages  stage  = Stages.O; // Output
	}

	Map<String, CompilerInstructions> fn2ci();

	USE use();

	CIS _cis();
}

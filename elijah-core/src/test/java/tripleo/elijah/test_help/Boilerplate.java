package tripleo.elijah.test_help;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.nextgen.rosetta.Rosetta;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.gdm.GDM_IdentExpression;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.gen_fn_c.GenFnC;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.OutputFileFactory;
import tripleo.elijah.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah.stages.logging.ElLog;

public class Boilerplate {
	public  Compilation        comp;
	public  ICompilationAccess aca;
	public  ProcessRecord      pr;
	public  GenerateFiles      generateFiles;
	private CompilationRunner  cr;
	OS_Module module;

	public void get() {
//		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());
		comp = new CompilationImpl(new StdErrSink(), new IO());









		comp.reports().turnAllOutputOff();







		final ICompilationAccess aca1 = ((CompilationImpl) comp)._access();
		if (aca1 != null) {
			aca = aca1;
		} else {
			assert false;
			aca = new DefaultCompilationAccess(comp);
		}

		CR_State crState;
		crState = new CR_State(aca);
		cr = new CompilationRunner(aca, crState, () -> new CompilationBus(aca.getCompilation().getCompilationEnclosure()));
		crState.setRunner(cr);

		comp.getCompilationEnclosure().setCompilationRunner(cr);

		//crState = comp.getCompilationEnclosure().getCompilationRunner().crState;
		crState.ca();
		assert comp.getCompilationEnclosure().getCompilationRunner().crState != null; // always true

		pr            = cr.crState.pr;

		if (module != null) {
			module.setParent(comp);
		}
	}

	public void getGenerateFiles(final @NotNull OS_Module mod) {
		// NOTE 11/08:
		//  fileGen can be null for [GetRealTargetNameTest], but (may) fail under other circumstances
		final GenerateResultEnv fileGen = new GenerateResultEnv(null, null, null, null, null);
		generateFiles = OutputFileFactory.create(Compilation.CompilationAlways.defaultPrelude(),
												 new OutputFileFactoryParams(mod,
																			 comp.getCompilationEnclosure()),
												 fileGen);
	}

	public OS_Module defaultMod() {
		if (module == null) {
			module = new OS_ModuleImpl();
		}
		if (comp != null) {
			module.setParent(comp);
		}

		return module;
	}

	public DeducePhase getDeducePhase() {
		return pipelineLogic().dp;
	}

	public PipelineLogic pipelineLogic() {
		return pr.pipelineLogic();
	}

	public DeduceTypes2 defaultDeduceTypes2(final OS_Module mod) {
		final DeducePhase phase = getDeducePhase();
		return Rosetta.create(new DeduceTypes2Request(mod, phase, ElLog.Verbosity.VERBOSE));
	}

	@NotNull
	public GenerateFunctions defaultGenerateFunctions() {
		final GenFnC bc = new GenFnC();
		bc.set(pipelineLogic());
		bc.set(comp.pa());
		return new GenerateFunctions(defaultMod(), bc);
	}

	//public void fixTables(final GDM_IdentExpression gdm, final OS_Module aMod, final @NotNull BaseEvaFunction aBaseEvaFunction) {
	//	gdm.onIdentTableEntry(ite -> {
	//		fixTables(gdm, aMod, ite._generatedFunction());
	//	});
	//}

	public void fixTables(final GDM_IdentExpression gdm, final OS_Module aMod, final EvaFunction aGeneratedFunction) {
		var d2 = defaultDeduceTypes2(aMod);

		gdm.onIdentTableEntry(ite -> {
			ite._fix_table(d2, aGeneratedFunction);
		});
	}

	public void fixTables(final GDM_IdentExpression gdm, final OS_Module aMod) {
		var d2 = defaultDeduceTypes2(aMod);

		gdm.onIdentTableEntry(ite -> {
			final BaseEvaFunction evaFunction = ite._generatedFunction();
			ite._fix_table(d2, evaFunction);
		});
	}
}

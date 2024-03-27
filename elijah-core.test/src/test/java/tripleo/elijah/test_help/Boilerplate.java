package tripleo.elijah.test_help;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.IO;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_congenial.comp.StdErrSink;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess;
import tripleo.elijah_durable_congenial.comp.i.ProcessRecord;
import tripleo.elijah_durable_congenial.comp.internal.*;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.lang.impl.OS_ModuleImpl;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah_durable_congenial.nextgen.rosetta.Rosetta;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.gdm.GDM_IdentExpression;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn_c.GenFnC;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_durable_congenial.stages.gen_generic.OutputFileFactory;
import tripleo.elijah.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

public class Boilerplate {
	public  Compilation        comp;
	public  ICompilationAccess aca;
	public  ProcessRecord      pr;
	public  GenerateFiles      generateFiles;
	OS_Module module;
	private CompilationRunner cr;

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

	@NotNull
	public GenerateFunctions defaultGenerateFunctions() {
		final GenFnC bc = new GenFnC();
		bc.set(pipelineLogic());
		bc.set(comp.pa());
		return new GenerateFunctions(defaultMod(), bc);
	}

	public PipelineLogic pipelineLogic() {
		return pr.pipelineLogic();
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

	public void fixTables(final GDM_IdentExpression gdm, final OS_Module aMod, final EvaFunction aGeneratedFunction) {
		var d2 = defaultDeduceTypes2(aMod);

		gdm.onIdentTableEntry(ite -> {
			ite._fix_table(d2, aGeneratedFunction);
		});
	}

	public DeduceTypes2 defaultDeduceTypes2(final OS_Module mod) {
		final DeducePhase phase = getDeducePhase();
		return Rosetta.create(new DeduceTypes2Request(mod, phase, ElLog.Verbosity.VERBOSE));
	}

	//public void fixTables(final GDM_IdentExpression gdm, final OS_Module aMod, final @NotNull BaseEvaFunction aBaseEvaFunction) {
	//	gdm.onIdentTableEntry(ite -> {
	//		fixTables(gdm, aMod, ite._generatedFunction());
	//	});
	//}

	public DeducePhase getDeducePhase() {
		return pipelineLogic().dp;
	}

	public void fixTables(final GDM_IdentExpression gdm, final OS_Module aMod) {
		var d2 = defaultDeduceTypes2(aMod);

		gdm.onIdentTableEntry(ite -> {
			final BaseEvaFunction evaFunction = ite._generatedFunction();
			ite._fix_table(d2, evaFunction);
		});
	}
}

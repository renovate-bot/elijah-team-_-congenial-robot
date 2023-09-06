package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.notation.GM_GenerateModule;
import tripleo.elijah.comp.notation.GM_GenerateModuleRequest;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSink;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSinkEnv;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.output.NG_OutputFunction;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingNamespace;
import tripleo.util.buffer.Buffer;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

class AmazingFunction implements Amazing {
	private final NG_OutputFunction                of;
	private final BaseEvaFunction                  f;
	private final OS_Module                        mod;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final GenerateResult                   result;
	private final IPipelineAccess                  pa;

	public AmazingFunction(final @NotNull BaseEvaFunction aBaseEvaFunction,
						   final @NotNull WPIS_GenerateOutputs.OutputItems aOutputItems,
						   final @NotNull GenerateResult aGenerateResult,
						   final @NotNull IPipelineAccess aPa) {
		// given
		f      = aBaseEvaFunction;
		mod    = aBaseEvaFunction.module();
		itms   = aOutputItems;
		pa     = aPa;
		result = aGenerateResult;

		// created
		of = new NG_OutputFunction();
	}

	void waitGenC(final GenerateC ggc) {
		// TODO latch
		pa.getAccessBus().subscribePipelineLogic(pl0 -> {
			// FIXME check arguments
			var env = new GN_GenerateNodesIntoSinkEnv(List_of(),
													  new DefaultGenerateResultSink(pa),
													  pl0.mods(),
													  ElLog.Verbosity.VERBOSE,
													  new Old_GenerateResult(),
													  pa,
													  pa.getCompilationEnclosure());
			//var mod = pte.__gf.getFD().getContext().module();
			var generateModuleRequest = new GM_GenerateModuleRequest(new GN_GenerateNodesIntoSink(env), mod, env);
			var generateModule        = new GM_GenerateModule(generateModuleRequest);

			var fileGen = new GenerateResultEnv(new MyGenerateResultSink(of), result, new WorkManager(), new WorkList(), generateModule);

			var generateModuleResult = generateModule.getModuleResult(fileGen.wm(), fileGen.resultSink());

			if (f instanceof EvaFunction ff) {
				ggc.generateCodeForMethod(fileGen, ff);
			} else if (f instanceof EvaConstructor fc) {
				ggc.generateCodeForConstructor(fileGen, fc);
			}

			itms.addItem(of);
		});
	}

	public OS_Module mod() {
		return mod;
	}

	private static class MyGenerateResultSink implements GenerateResultSink {
		private final NG_OutputFunction of;

		public MyGenerateResultSink(final NG_OutputFunction aOf) {
			of = aOf;
		}

		@Override
		public void add(final EvaNode node) {
			throw new UnintendedUseException();
		}

		@Override
		public void addClass_0(final GarishClass aGarishClass, final Buffer aImplBuffer, final Buffer aHeaderBuffer) {
			throw new UnintendedUseException();
		}

		@Override
		public void addClass_1(final GarishClass aGarishClass, final GenerateResult aGenerateResult, final GenerateC aGenerateC) {
			throw new UnintendedUseException();
		}

		@Override
		public void addFunction(final BaseEvaFunction aGf, final List<C2C_Result> aRs, final GenerateFiles aGenerateFiles) {
			of.setFunction(aGf, aGenerateFiles, aRs);
		}

		@Override
		public void additional(final GenerateResult aGenerateResult) {
			throw new UnintendedUseException();
		}

		@Override
		public void addNamespace_0(final GarishNamespace aLivingNamespace, final Buffer aImplBuffer, final Buffer aHeaderBuffer) {
			throw new UnintendedUseException();
		}

		@Override
		public void addNamespace_1(final GarishNamespace aGarishNamespace, final GenerateResult aGenerateResult, final GenerateC aGenerateC) {
			throw new UnintendedUseException();
		}

		@Override
		public @Nullable LivingClass getLivingClassForEva(final EvaClass aEvaClass) {
			throw new UnintendedUseException();
		}

		@Override
		public @Nullable LivingNamespace getLivingNamespaceForEva(final EvaNamespace aEvaClass) {
			throw new UnintendedUseException();
		}
	}
}

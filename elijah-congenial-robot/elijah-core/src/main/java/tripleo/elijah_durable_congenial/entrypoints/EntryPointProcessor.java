package tripleo.elijah_durable_congenial.entrypoints;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.nextgen.rosetta.Rosetta;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.NULL_DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce_r.RegisterClassInvocation_resp;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;
import tripleo.elijah_durable_congenial.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_congenial.world.i.LivingRepo;

public interface EntryPointProcessor {
	static @NotNull EntryPointProcessor dispatch(final EntryPoint ep, final DeducePhase aDeducePhase, final WorkList aWl, final GenerateFunctions aGenerateFunctions) {
		if (ep instanceof MainClassEntryPoint) {
			return new EPP_MCEP((MainClassEntryPoint) ep, aDeducePhase, aWl, aGenerateFunctions);
		} else if (ep instanceof ArbitraryFunctionEntryPoint) {
			return new EPP_AFEP((ArbitraryFunctionEntryPoint) ep, aDeducePhase, aWl, aGenerateFunctions);
		}

		throw new IllegalStateException();
	}

	void process();

	class EPP_AFEP implements EntryPointProcessor {

		private final ArbitraryFunctionEntryPoint        afep;
		private final EPP_MCEP.@NotNull EppCodeRegistrar codeRegistrar;
		private final DeducePhase                        deducePhase;
		private final GenerateFunctions                  generateFunctions;
		private final WorkList                           wl;

		public EPP_AFEP(final ArbitraryFunctionEntryPoint aEp, final DeducePhase aDeducePhase, final WorkList aWl, final GenerateFunctions aGenerateFunctions) {
			afep              = aEp;
			deducePhase       = aDeducePhase;
			wl                = aWl;
			generateFunctions = aGenerateFunctions;

			codeRegistrar = new EPP_MCEP.EppCodeRegistrar(deducePhase._compilation());
		}

		@Override
		public void process() {
			final @NotNull FunctionDef     f  = afep.getFunction();
			final @NotNull ClassInvocation ci = deducePhase.registerClassInvocation((ClassStatement) afep.getParent(), null); // !!

			final WlGenerateClass job = new WlGenerateClass(generateFunctions, ci, deducePhase.generatedClasses, codeRegistrar);
			wl.addJob(job);

			final @NotNull FunctionInvocation fi = deducePhase.newFunctionInvocation(f, null, ci);
//				fi.setPhase(phase);
			final WlGenerateFunction job1 = new WlGenerateFunction(generateFunctions, fi, codeRegistrar);
			wl.addJob(job1);
		}
	}

	class EPP_MCEP implements EntryPointProcessor {
		final @NotNull ICodeRegistrar codeRegistrar;
		private final  DeducePhase    deducePhase;
		private final  GenerateFunctions   generateFunctions;
		private final  MainClassEntryPoint mcep;
		private final  WorkList            wl;

		public EPP_MCEP(final MainClassEntryPoint aEp, final DeducePhase aDeducePhase, final WorkList aWl, final GenerateFunctions aGenerateFunctions) {
			mcep              = aEp;
			deducePhase       = aDeducePhase;
			wl                = aWl;
			generateFunctions = aGenerateFunctions;

			codeRegistrar = new EppCodeRegistrar(deducePhase._compilation());
		}

		@Override
		public void process() {
			final @NotNull ClassStatement cs = mcep.getKlass();
			final @NotNull FunctionDef    f  = mcep.getMainFunction();

			final ClassInvocation           ci;

			final Eventual<ClassInvocation> rci = new Eventual<>();
			rci.then(ci1 -> {
				deducePhase.generatePhase.setCodeRegistrar(codeRegistrar);

				final @NotNull WlGenerateClass job = new WlGenerateClass(generateFunctions, ci1, deducePhase.generatedClasses, codeRegistrar);
				wl.addJob(job);

				final @NotNull FunctionInvocation fi = deducePhase.newFunctionInvocation(f, null, ci1);
//				fi.setPhase(phase);
				final @NotNull WlGenerateFunction job1 = new WlGenerateFunction(generateFunctions, fi, codeRegistrar);
				wl.addJob(job1);
			});

			final boolean _Apr04 = false;
			// README still does not solve problem of getting dt2
			//  investigate if actually needeed
			//  then set this to true
			if (_Apr04) {
				final RegisterClassInvocation_resp resp = new RegisterClassInvocation_resp();
				Rosetta.create(new RegisterClassInvocation_env(cs, null, deducePhase), resp).apply();
				resp.onSuccess(rci::resolve);
			} else {
				ci = deducePhase.registerClassInvocation(cs, null, new NULL_DeduceTypes2());
				rci.resolve(ci);
			}

			assert rci.isResolved();
		}
	}

	class EppCodeRegistrar implements ICodeRegistrar {
		private final Compilation compilation;

		public EppCodeRegistrar(Compilation aC) {
			compilation = aC;
		}

		@Override
		public void registerClass(final EvaClass aClass) {
			compilation.livingRepo().addClass(aClass, LivingRepo.Add.MAIN_CLASS);
		}

		@Override
		public void registerClass1(final EvaClass aClass) {
			compilation.livingRepo().addClass(aClass, LivingRepo.Add.NONE);
		}

		@Override
		public void registerFunction(final BaseEvaFunction aFunction) {
			compilation.livingRepo().addFunction(aFunction, LivingRepo.Add.MAIN_FUNCTION);
		}

		@Override
		public void registerFunction1(final BaseEvaFunction aFunction) {
			compilation.livingRepo().addFunction(aFunction, LivingRepo.Add.NONE);
		}

		@Override
		public void registerNamespace(final EvaNamespace aNamespace) {
			compilation.livingRepo().addNamespace(aNamespace, LivingRepo.Add.NONE);
		}
	}

}

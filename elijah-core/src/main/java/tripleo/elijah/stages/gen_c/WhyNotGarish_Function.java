package tripleo.elijah.stages.gen_c;

import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.notation.GM_GenerateModule;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.nextgen.rosetta.Rosetta;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;

public class WhyNotGarish_Function extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final BaseEvaFunction                               gf;
	private final GenerateC                                     generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();

	public WhyNotGarish_Function(final BaseEvaFunction aGf, final GenerateC aGenerateC) {
		gf        = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
	}

	private void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		if (gf.getFD() == null) assert false; //return; // FIXME why? when?
		Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC.LOG);
		gcfm.generateCodeForMethod(deduced(gf), aFileGen);
	}

	@Contract(pure = true)
	private @Nullable BaseEvaFunction deduced(final @NotNull BaseEvaFunction aEvaFunction) {
		final GM_GenerateModule  generateModule    = generateC.getFileGen().generateModule();
		final DeducePhase        deducePhase       = generateModule.pa().getDeducePhase();
		final ICompilationAccess compilationAccess = generateModule.pa().getCompilationEnclosure().getCompilationAccess();

		final DeduceTypes2Request deduceTypes2Request = new DeduceTypes2Request(aEvaFunction.module(),
																				deducePhase,
																				compilationAccess.testSilence());
		final DeduceTypes2 dt2 = Rosetta.create(deduceTypes2Request);

		dt2.deduceOneFunction((EvaFunction) aEvaFunction, deducePhase);

		return aEvaFunction;
	}

	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		if (!fileGenPromise.isResolved())
			fileGenPromise.resolve(aFileGen);
		else {
			var c = generateC._ce().getCompilation();
			if (c.reports().outputOn(Finally.Outs.Out_5757)) {
				System.out.println("twice for " + generateC);
			}
		}
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	@Override
	public BaseEvaFunction getGf() {
		return gf;
	}
}

package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess;
import tripleo.elijah_durable_congenial.comp.notation.GM_GenerateModule;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request_TWO;
import tripleo.elijah_durable_congenial.nextgen.rosetta.Rosetta;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;

import java.util.Optional;
import java.util.function.Consumer;

public class WhyNotGarish_Function extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final BaseEvaFunction             gf;
	private final GenerateC                   generateC;
	private final Eventual<GenerateResultEnv> fileGenPromise = new Eventual<>();

	public WhyNotGarish_Function(final BaseEvaFunction aGf, final GenerateC aGenerateC) {
		gf        = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
	}

	private void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
//		if (gf.getFD() == null) assert false; //return; // FIXME why? when?
		Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC.elLog());

		deduced(gf, (DeducedBaseEvaFunction dgf) -> {
			dgf.generateCodeForMethod(gcfm, aFileGen);
		});
	}

	private void deduced(final BaseEvaFunction aEvaFunction, final Consumer<DeducedBaseEvaFunction> c) {
		final GM_GenerateModule generateModule = generateC.getFileGen().generateModule();
		final DeduceTypes2Request deduceTypes2Request = new DeduceTypes2Request(aEvaFunction.module(),
																				generateModule.getDeducePhase(),
																				generateModule.testSilence());
		Rosetta.create(deduceTypes2Request, new DeduceTypes2Request_TWO())
				.pass(aEvaFunction, c);
	}

	@Contract(pure = true)
	private @Nullable BaseEvaFunction deduced(final @NotNull BaseEvaFunction aEvaFunction) {
		final GM_GenerateModule  generateModule    = generateC.getFileGen().generateModule();
		final DeducePhase        deducePhase       = generateModule.pa().getDeducePhase();
		final ICompilationAccess compilationAccess = generateModule.pa().getCompilationEnclosure().getCompilationAccess();

		final DeduceTypes2Request deduceTypes2Request = new DeduceTypes2Request(aEvaFunction.module(),
																				deducePhase,
																				compilationAccess.testSilence());

		final DeduceTypes2 deduceTypes2 = Rosetta.create(deduceTypes2Request);

		deduceTypes2.deduceOneFunction((EvaFunction) aEvaFunction, deduceTypes2.getPhase());

		return aEvaFunction;
	}

	@Override
	public BaseEvaFunction getGf() {
		return gf;
	}

	@Override
	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		if (!fileGenPromise.isResolved()) {
			fileGenPromise.resolve(aFileGen);
		} else {
			//assert false; // FIXME 11/11
			var c = generateC._ce().getCompilation();
			if (true) {
				System.out.println("[WhyNotGarish_Function#resolveFileGenPromise] twice for " + generateC);
			}
		}
	}

	@Override
	public Optional<GenerateC> getGenerateC() {
		if (!fileGenPromise.isResolved()) {
			return Optional.empty();
		}

		final @NotNull GenerateFiles[] xx = new GenerateFiles[1];
		fileGenPromise.then(fg -> xx[0] = fg.getGenerateFiles());

		return Optional.of((GenerateC) xx[0]);
	}
}

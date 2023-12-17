package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.DebugFlags;
import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkList;

import java.util.List;
import java.util.Optional;

public class WhyNotGarish_Constructor extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final EvaConstructor                                gf;
	private final GenerateC                                     generateC;

	public WhyNotGarish_Constructor(final EvaConstructor aGf, final GenerateC aGenerateC) {
		gf        = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
	}

	void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		final Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC.elLog());


		var yf = generateC.a_lookup(gf);


		// TODO separate into method and method_header??
		final C2C_CodeForConstructor cfm = new C2C_CodeForConstructor(gcfm, gf, aFileGen, yf);

		//cfm.calculate();
		final List<C2C_Result> rs   = cfm.getResults();
		final GenerateResult   gr   = new Old_GenerateResult();
		final GCFC             gcfc = new GCFC(rs, gf, gr); // TODO 08/12 preload this??

		gf.reactive().add(gcfc);

		if (!DebugFlags.GenerateC_MANUAL_DISABLED) {
			gcfc.respondTo(generateC);
		}

		// FIXME 06/17
		final GenerateResultSink sink = aFileGen.resultSink();

		if (sink != null) {
			sink.addFunction(gf, rs, generateC);
		} else {
			System.err.println("sink failed");
		}
	}

	@Override
	public BaseEvaFunction getGf() {
		return gf;
	}

	@Override
	public EvaConstructor cheat() {
		return gf;
	}

	@Override
	public Optional<GenerateC> getGenerateC() {
		if (!hasFileGen())
			return null;
		final @NotNull GenerateFiles[] xx = new GenerateFiles[1];
		fileGenPromise.then(fg -> {
			xx[0] = fg.generateModule().gmr().getGenerateFiles(null);
		});
		return Optional.of((GenerateC) xx[0]);
	}

	@NotNull String getConstructorNameText() {
		final IdentExpression constructorName = gf.getFD().getNameNode();

		final String constructorNameText;
		if (constructorName == LangGlobals.emptyConstructorName) {
			constructorNameText = "";
		} else {
			constructorNameText = constructorName.getText();
		}
		return constructorNameText;
	}

	public void postGenerateCodeForConstructor(final WorkList aWl, final GenerateResultEnv aFileGen) {
		for (IdentTableEntry identTableEntry : gf.idte_list) {
			//IdentTableEntry.;


			identTableEntry.reactive().addResolveListener((IdentTableEntry x) -> {
				generateIdent(x, aFileGen);
			});

			if (identTableEntry.isResolved()) {
				generateIdent(identTableEntry, aFileGen);
			}
		}
		for (ProcTableEntry pte : gf.prte_list) {
//                      ClassInvocation ci = pte.getClassInvocation();
			FunctionInvocation fi = pte.getFunctionInvocation();
			if (fi == null) {
				// TODO constructor
				int y = 2;
			} else {
				BaseEvaFunction gf = fi.getEva();
				if (gf != null) {
					aWl.addJob(new GenerateC.WlGenerateFunctionC(aFileGen, gf, generateC));
				}
			}
		}
	}

	private void generateIdent(@NotNull IdentTableEntry identTableEntry, @NotNull GenerateResultEnv aFileGen) {
		assert identTableEntry.isResolved();

		final @NotNull EvaNode   x           = identTableEntry.resolvedType();
		final WorkList           wl          = aFileGen.wl();

		if (x instanceof final EvaClass evaClass) {
			generateC.generate_class(aFileGen, evaClass);
		} else if (x instanceof final EvaFunction evaFunction) {
			wl.addJob(new GenerateC.WlGenerateFunctionC(aFileGen, evaFunction, generateC));
		} else {
			generateC.elLog().err(x.toString());
			throw new NotImplementedException();
		}
	}
}

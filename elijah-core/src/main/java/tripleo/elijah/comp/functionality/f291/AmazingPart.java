package tripleo.elijah.comp.functionality.f291;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;

import tripleo.elijah.comp.i.CompilationEnclosure;

import tripleo.elijah.stages.write_stage.pipeline_impl.WritePipelineSharedState;

import tripleo.elijah.sanaa.ElIntrinsics;

import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_c.GenerateC;

import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingFunction;
import tripleo.elijah.world.i.LivingNamespace;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AmazingPart {
	private final WPIS_GenerateOutputs wpisGenerateOutputs;
	private final CompilationEnclosure compilationEnclosure;
	private final List<Amazing>        amazings;
	private final OutputItems          itms;

	public AmazingPart(final WPIS_GenerateOutputs aWPISGenerateOutputs,
					   final OutputItems aItms,
					   final CompilationEnclosure aCompilationEnclosure) {
		wpisGenerateOutputs  = aWPISGenerateOutputs;
		compilationEnclosure = aCompilationEnclosure;
		itms                 = aItms;
		amazings             = new ArrayList<>(/*totalCount*/);
	}

	public void handle(final S aS) {
		switch (aS) {
		case RunSignal -> {
			for (Amazing amazing : amazings) {
				amazing.run();
			}
		}
		default -> throw new IllegalStateException("Unexpected value: " + aS);
		}
	}

	public void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
		final Eventual<GenerateC> generateCEventual = B.INSTANCE.lookupModule(mod);
		ElIntrinsics.checkNotNull(generateCEventual);
		generateCEventual.then(cb::accept);
	}

	public void reverseOffer(final LivingClass lc) {
		final EvaClass     c            = lc.evaNode();
		final AmazingClass amazingClass = new AmazingClass(c, itms, compilationEnclosure);
		waitGenC(amazingClass.mod(), amazingClass::waitGenC);
		amazings.add(amazingClass);
	}

	public void reverseOffer(final LivingFunction lf) {
		final @Nullable BaseEvaFunction f               = lf.evaNode();
		final WritePipelineSharedState  st              = wpisGenerateOutputs.__st();
		final GenerateResult            result          = st.getGr();
		final AmazingFunction           amazingFunction = new AmazingFunction(f, itms, result, compilationEnclosure.getPipelineAccess());
		waitGenC(amazingFunction.mod(), amazingFunction::waitGenC);
		amazings.add(amazingFunction);
	}

	public void reverseOffer(final LivingNamespace ln) {
		final EvaNamespace     n                = ln.evaNode();
		final AmazingNamespace amazingNamespace = new AmazingNamespace(n, itms, compilationEnclosure.getPipelineAccess());
		waitGenC(amazingNamespace.mod(), amazingNamespace::waitGenC);
		amazings.add(amazingNamespace);
	}
}

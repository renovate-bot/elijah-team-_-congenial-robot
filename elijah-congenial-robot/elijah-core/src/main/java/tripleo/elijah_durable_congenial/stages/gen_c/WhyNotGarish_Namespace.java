package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;

public class WhyNotGarish_Namespace implements WhyNotGarish_Item {
	private final EvaNamespace                                  en;
	private final GenerateC                                     generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();

	public WhyNotGarish_Namespace(final EvaNamespace aEn, final GenerateC aGenerateC) {
		en        = aEn;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
	}

	public String getTypeNameString() {
		return GenerateC.GetTypeName.forGenNamespace(en);
	}

	private void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		NotImplementedException.raise();
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}
}

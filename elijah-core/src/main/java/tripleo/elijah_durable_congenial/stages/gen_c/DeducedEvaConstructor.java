package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.NotNull;

public interface DeducedEvaConstructor extends DeducedEvaFunctionBase {
	WhyNotGarish_Constructor getWhyNotGarishFunction(final @NotNull GenerateC aGc);

	default WhyNotGarish_Constructor a_lookup(GenerateC aGc) {
		return getWhyNotGarishFunction(aGc);
	}
}

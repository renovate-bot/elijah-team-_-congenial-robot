package tripleo.elijah_durable_congenial.stages.gen_generic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum OutputFileFactory {
	;

	@Contract("_, _, _ -> new")
	public static @NotNull GenerateFiles create(final @NotNull String lang,
												final @NotNull OutputFileFactoryParams params,
												final GenerateResultEnv aFileGen) {
		if (Objects.equals(lang, "c")) {
			final OS_Module mod = params.getMod();

			if (mgfMap.containsKey(mod)) {
				return mgfMap.get(mod);
			}

			final var ce = params.getCompilationEnclosure();
			final GenerateFiles generateC = new GenerateC(params, aFileGen);

			ce.spi(generateC);

			mgfMap.put(mod, generateC);

			return generateC;
		} else
			throw new NotImplementedException();
	}

	private static final Map<OS_Module, GenerateFiles> mgfMap = new HashMap<>();
}

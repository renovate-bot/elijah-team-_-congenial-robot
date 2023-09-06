package tripleo.elijah.lang.nextgen.names.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;

public class ENU_TypeTransitiveOver implements EN_Understanding {
	private final @NotNull ProcTableEntry pte;

	public ENU_TypeTransitiveOver(final @NotNull ProcTableEntry aPte) {

		pte = aPte;
	}
}

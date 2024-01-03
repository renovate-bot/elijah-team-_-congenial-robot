package tripleo.elijah.stages.deduce.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;

public class ProcedureCallUnderstanding implements DR_Ident.Understanding {
	private final ProcTableEntry procTableEntry;

	public ProcedureCallUnderstanding(final ProcTableEntry aProcTableEntry) {
		procTableEntry = aProcTableEntry;
	}

	@Override
	public @NotNull String asString() {
		return "ProcedureCallUnderstanding: " + procTableEntry.__debug_expression;
	}

	@Override
	public EN_Understanding getENU() {
		return new EN_Understanding() {};
	}
}

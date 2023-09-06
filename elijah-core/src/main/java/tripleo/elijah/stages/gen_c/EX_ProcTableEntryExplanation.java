package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;

public class EX_ProcTableEntryExplanation implements EX_Explanation {
	private final @NotNull DeduceElement3_ProcTableEntry pte;

	public EX_ProcTableEntryExplanation(final @NotNull DeduceElement3_ProcTableEntry aPte) {
		pte = aPte;
	}

	@Override
	public @NotNull String message() {
		return "EX_ProcTableEntryExplanation";
	}
}

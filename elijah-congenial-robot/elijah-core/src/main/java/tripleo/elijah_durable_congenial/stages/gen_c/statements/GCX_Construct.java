package tripleo.elijah_durable_congenial.stages.gen_c.statements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah_durable_congenial.stages.gen_c.GI_ProcIA;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah.util.Mode;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_durable_congenial.stages.gen_c.GI_ProcIA;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;

public class GCX_Construct implements EG_Statement {

	private final GI_ProcIA   gi_proc;
	private final Instruction instruction1;
	private final GenerateC   gc1;

	public GCX_Construct(final GI_ProcIA aGiProc, final Instruction aInstruction1, final GenerateC aGc1) {
		gi_proc      = aGiProc;
		instruction1 = aInstruction1;
		gc1          = aGc1;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("GCX_Construct");
	}

	@Override
	public @Nullable String getText() {
		final Operation2<EG_Statement> s = gi_proc.action_CONSTRUCT(instruction1, gc1);
		if (s.mode() == Mode.SUCCESS) {
			return s.success().getText();
		}
		return null;
	}
}

package tripleo.elijah_durable_congenial.stages.deduce.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

public class SGTA_SetDrType implements setup_GenType_Action {
	private final DR_Type drType;

	public SGTA_SetDrType(final DR_Type aDR_type) {
		drType = aDR_type;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setDrType(drType);
	}
}

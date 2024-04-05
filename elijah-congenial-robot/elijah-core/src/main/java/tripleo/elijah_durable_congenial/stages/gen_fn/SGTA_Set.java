package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.OS_Type;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah_durable_congenial.lang.i.OS_Type;

public class SGTA_Set implements setup_GenType_Action {
	private final @NotNull OS_Type type;

	public SGTA_Set(final @NotNull OS_Type aType) {
		type = aType;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.set(type);
	}
}

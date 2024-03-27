package tripleo.elijah_durable_congenial.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

public interface setup_GenType_Action {
	void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena);
}

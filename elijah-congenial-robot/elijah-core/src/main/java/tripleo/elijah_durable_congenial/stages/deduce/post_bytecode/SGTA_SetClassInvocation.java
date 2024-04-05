package tripleo.elijah_durable_congenial.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

public class SGTA_SetClassInvocation implements setup_GenType_Action {
	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		final ClassInvocation ci = arena.get("ci");
		gt.setCi(ci);
	}
}

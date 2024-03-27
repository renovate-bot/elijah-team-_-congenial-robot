package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.deduce.IInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah_durable_congenial.stages.deduce.IInvocation;

public class SGTA_SetCi implements setup_GenType_Action {
	private final IInvocation invocation;

	public SGTA_SetCi(final IInvocation aInvocation) {
		invocation = aInvocation;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setCi(invocation);
	}
}

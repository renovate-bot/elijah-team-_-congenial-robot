package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;

public class SGTA_SetFunctionInvocation implements setup_GenType_Action {
	private final FunctionInvocation fi;

	public SGTA_SetFunctionInvocation(final FunctionInvocation aFi) {
		fi = aFi;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setFunctionInvocation(fi);

	}
}

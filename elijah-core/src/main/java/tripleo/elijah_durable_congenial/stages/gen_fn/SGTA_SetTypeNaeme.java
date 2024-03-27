package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.OS_Type;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah_durable_congenial.lang.i.OS_Type;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;

public class SGTA_SetTypeNaeme implements setup_GenType_Action {
	private final OS_Type type;

	public SGTA_SetTypeNaeme(final OS_Type aType) {
		type = aType;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setTypeName(type);
	}
}

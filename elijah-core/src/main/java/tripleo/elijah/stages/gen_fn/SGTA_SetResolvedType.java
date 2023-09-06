package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action_Arena;

public class SGTA_SetResolvedType implements setup_GenType_Action {
	private final OS_Type osType;

	public SGTA_SetResolvedType(final OS_Type aOSType) {
		osType = aOSType;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setResolved(osType);
	}
}

package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah.stages.deduce.post_bytecode.setup_GenType_Action_Arena;

public class SGTA_CopyGenType implements setup_GenType_Action {

	private final GenType _gt;

	public SGTA_CopyGenType(GenType aGenType) {
		_gt = aGenType;
	}

	@Override
	public void run(@NotNull GenType gt, @NotNull setup_GenType_Action_Arena arena) {
		gt.copy(_gt);
	}

}

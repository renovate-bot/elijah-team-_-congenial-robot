package tripleo.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_fn.GenType;

public interface setup_GenType_Action {
	void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena);
}

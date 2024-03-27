package tripleo.elijah_durable_congenial.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.setup_GenType_Action_Arena;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;

public class SGTA_RestReolvedn implements setup_GenType_Action {
	private final NamespaceStatement parent;

	public SGTA_RestReolvedn(final NamespaceStatement aParent) {
		parent = aParent;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setResolvedn(parent);
	}
}

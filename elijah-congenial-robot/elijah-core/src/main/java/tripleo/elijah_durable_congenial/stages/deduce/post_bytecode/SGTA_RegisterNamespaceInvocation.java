package tripleo.elijah_durable_congenial.stages.deduce.post_bytecode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.NamespaceInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.NamespaceInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

public class SGTA_RegisterNamespaceInvocation implements setup_GenType_Action {
	private final NamespaceStatement namespaceStatement;
	private final DeducePhase        phase;

	@Contract(pure = true)
	public SGTA_RegisterNamespaceInvocation(final NamespaceStatement aNamespaceStatement, final DeducePhase aPhase) {
		namespaceStatement = aNamespaceStatement;
		phase              = aPhase;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		final NamespaceInvocation nsi = phase.registerNamespaceInvocation(namespaceStatement);
		arena.put("nsi", nsi);
	}
}

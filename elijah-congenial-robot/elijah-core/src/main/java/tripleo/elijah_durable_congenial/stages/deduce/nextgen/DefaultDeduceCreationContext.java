package tripleo.elijah_durable_congenial.stages.deduce.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.Deduce_CreationClosure;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GeneratePhase;

public class DefaultDeduceCreationContext implements DeduceCreationContext {
	private final DeduceTypes2 deduceTypes2;

	public DefaultDeduceCreationContext(DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	@Override
	public Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(final @NotNull FunctionInvocation aFunctionInvocation) {
		final GeneratePhase generatePhase = getGeneratePhase();
		final DeducePhase   deducePhase   = getDeducePhase();

		final Deduce_CreationClosure cl = new Deduce_CreationClosure(deducePhase, this, deduceTypes2, generatePhase);

		return aFunctionInvocation.makeGenerated__Eventual(cl, null);
	}

	@Override
	public DeduceTypes2 getDeduceTypes2() {
		return deduceTypes2;
	}

	@Override
	@NotNull
	public DeducePhase getDeducePhase() {
		return deduceTypes2.getPhase();
	}

	@Override
	@NotNull
	public GeneratePhase getGeneratePhase() {
		return getDeducePhase().generatePhase;
	}
}

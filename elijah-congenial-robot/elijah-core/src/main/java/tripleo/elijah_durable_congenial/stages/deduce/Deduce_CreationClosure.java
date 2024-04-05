package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah_durable_congenial.stages.gen_fn.GeneratePhase;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah_durable_congenial.stages.gen_fn.GeneratePhase;

public record Deduce_CreationClosure(
		DeducePhase deducePhase,
		DeduceCreationContext dcc,
		DeduceTypes2 deduceTypes2,
		GeneratePhase generatePhase
) {
}

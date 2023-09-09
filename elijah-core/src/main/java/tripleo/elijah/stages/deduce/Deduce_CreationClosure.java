package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah.stages.gen_fn.GeneratePhase;

public record Deduce_CreationClosure(
		DeducePhase deducePhase,
		DeduceCreationContext dcc,
		DeduceTypes2 deduceTypes2,
		GeneratePhase generatePhase
) {
}

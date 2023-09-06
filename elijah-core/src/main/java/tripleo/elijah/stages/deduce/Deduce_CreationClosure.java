package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.deduce.nextgen.DeduceCreationContext;

public record Deduce_CreationClosure(DeducePhase deducePhase, DeduceCreationContext dcc, DeduceTypes2 deduceTypes2,
									 tripleo.elijah.stages.gen_fn.GeneratePhase generatePhase) {
}

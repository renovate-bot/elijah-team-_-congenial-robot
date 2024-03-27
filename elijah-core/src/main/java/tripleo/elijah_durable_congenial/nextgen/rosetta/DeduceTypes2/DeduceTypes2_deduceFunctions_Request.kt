package tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2

import tripleo.elijah_durable_congenial.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode

class DeduceTypes2_deduceFunctions_Request(
	val request: DeducePhase_deduceModule_Request,
	val listOfEvaFunctions: MutableIterable<EvaNode>,
	val b: Boolean,
	val deducePhase: DeducePhase,
)

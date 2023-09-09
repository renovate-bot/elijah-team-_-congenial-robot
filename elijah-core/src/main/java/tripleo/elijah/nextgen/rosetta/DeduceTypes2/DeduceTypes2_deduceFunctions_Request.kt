package tripleo.elijah.nextgen.rosetta.DeduceTypes2

import tripleo.elijah.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request
import tripleo.elijah.stages.deduce.DeducePhase
import tripleo.elijah.stages.gen_fn.EvaNode

class DeduceTypes2_deduceFunctions_Request(
        val request: DeducePhase_deduceModule_Request,
        val listOfEvaFunctions: MutableIterable<EvaNode>,
        val b: Boolean,
        val deducePhase: DeducePhase,
)

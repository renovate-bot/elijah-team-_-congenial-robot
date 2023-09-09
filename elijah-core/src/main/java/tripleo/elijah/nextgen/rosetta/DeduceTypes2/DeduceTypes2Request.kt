package tripleo.elijah.nextgen.rosetta.DeduceTypes2

import tripleo.elijah.lang.i.OS_Module
import tripleo.elijah.stages.deduce.DeducePhase
import tripleo.elijah.stages.logging.ElLog.Verbosity

//@JvmRecord
data class DeduceTypes2Request(val module: OS_Module,
                               val deducePhase: DeducePhase,
                               val verbosity: Verbosity)

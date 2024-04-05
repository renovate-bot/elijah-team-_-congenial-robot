package tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2

import tripleo.elijah_durable_congenial.lang.i.OS_Module
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase
import tripleo.elijah_durable_congenial.stages.logging.ElLog.Verbosity

//@JvmRecord
data class DeduceTypes2Request(val module: OS_Module,
                               val deducePhase: DeducePhase,
                               val verbosity: Verbosity)

package tripleo.elijah.stages.gen_fn_r

import tripleo.elijah.lang.i.ClassStatement
import tripleo.elijah.stages.deduce.ClassInvocation
import tripleo.elijah.stages.deduce.DeducePhase
import tripleo.elijah.stages.deduce.DeduceTypes2

data class RegisterClassInvocation_env(
		val ci: ClassInvocation,
		val deduceTypes2: DeduceTypes2?,
		val phase: DeducePhase?
) {
	constructor(
		classStatement: ClassStatement,
		deduceTypes2: DeduceTypes2,
		phase: DeducePhase
	) : this(
		ci = phase.registerClassInvocation(classStatement, deduceTypes2),
		deduceTypes2 = deduceTypes2,
		phase = phase
	)

	fun ci(): ClassInvocation {
		return ci
	}

	fun deduceTypes2(): DeduceTypes2 {
		return deduceTypes2!!
	}

	fun phase(): DeducePhase {
		return phase!!
	}
}

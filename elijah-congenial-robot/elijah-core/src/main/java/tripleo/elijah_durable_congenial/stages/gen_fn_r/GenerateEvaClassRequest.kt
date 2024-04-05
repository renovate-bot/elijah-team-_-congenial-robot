package tripleo.elijah_durable_congenial.stages.gen_fn_r

import tripleo.elijah_durable_congenial.lang.i.ClassStatement
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions

data class GenerateEvaClassRequest(
	val generateFunctions: GenerateFunctions,
	val classStatement: ClassStatement,
	val classInvocation: ClassInvocation,
	val passthruEnv: RegisterClassInvocation_env
)

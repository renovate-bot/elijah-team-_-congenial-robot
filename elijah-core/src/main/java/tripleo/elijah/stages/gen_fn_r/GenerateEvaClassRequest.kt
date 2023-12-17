package tripleo.elijah.stages.gen_fn_r

import tripleo.elijah.lang.i.ClassStatement
import tripleo.elijah.stages.deduce.ClassInvocation
import tripleo.elijah.stages.gen_fn.GenerateFunctions

data class GenerateEvaClassRequest(
		val generateFunctions: GenerateFunctions,
		val classStatement: ClassStatement,
		val classInvocation: ClassInvocation,
		val passthruEnv: RegisterClassInvocation_env
)

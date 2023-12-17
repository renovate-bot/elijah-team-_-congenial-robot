package tripleo.elijah.nextgen.rosetta.DeducePhase

import tripleo.elijah.lang.i.OS_Module
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request
import tripleo.elijah.nextgen.rosetta.Rosetta
import tripleo.elijah.stages.deduce.DeducePhase
import tripleo.elijah.stages.deduce.DeduceTypes2
import tripleo.elijah.stages.gen_fn.EvaNode
import tripleo.elijah.stages.logging.ElLog

data class DeducePhase_deduceModule_Request(
        val module: OS_Module,
        val listOfEvaFunctions: MutableIterable<EvaNode>,
        val verbosity: ElLog.Verbosity,
        val deducePhase: DeducePhase
) {
    private var createdDeduceTypes2: DeduceTypes2? = null

    fun createDeduceTypes2(): DeduceTypes2 {
        val deduceTypes2Request = DeduceTypes2Request(module = module, deducePhase = deducePhase, verbosity = verbosity)
        return Rosetta.create(deduceTypes2Request)
    }

    // Upon suggestion of idea, but here for reference
    //
    // https://www.baeldung.com/kotlin/companion-object
    //
    // There are times we need to use a companion object to define class members that are going to be used
    // independently of any instance of that class. The Kotlin compiler guarantees we will have one and only one
    // instance of a companion object. For those of us with a background in Java and C#, a companion object is
    // similar to static declarations.
    //
    // Kinda like a namespace
    //
    // That being said, I think this particular refactor is unnecessary
    //
    companion object {
        fun createDeduceTypes2Singleton(moduleRequest: DeducePhase_deduceModule_Request): DeduceTypes2 {
            if (moduleRequest.createdDeduceTypes2 == null) {
                moduleRequest.createdDeduceTypes2 = moduleRequest.createDeduceTypes2()
            }

            return moduleRequest.createdDeduceTypes2!!
        }
    }
}

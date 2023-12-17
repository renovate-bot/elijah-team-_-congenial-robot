package tripleo.elijah.nextgen.output

import tripleo.elijah.lang.i.OS_Module
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput
import tripleo.elijah.nextgen.outputstatement.EX_Explanation
import tripleo.elijah.stages.gen_generic.GenerateResult.TY
import tripleo.elijah.util.BufferTabbedOutputStream

data class NG_OutputClassStatement(
		private val __tos: BufferTabbedOutputStream,
		private val aModuleDependency: OS_Module,
		private val ty: TY,
) : NG_OutputStatement {
	private val text: String
//    private val moduleDependency: NG_OutDep

	init {
		text = __tos.buffer.text
//        moduleDependency    = NG_OutDep(aModuleDependency)
	}

	override fun getExplanation(): EX_Explanation {
		return EX_Explanation.withMessage("NG_OutputClassStatement")
	}

	override fun getText(): String {
		return text
	}

	override fun getTy(): TY {
		return ty
	}

	override fun getModuleInput(): EIT_ModuleInput {
//		val m = this.moduleDependency.module
//
//		return EIT_ModuleInput(m, m.getCompilation())
		return moduleInput_
	}

	private val moduleInput_: EIT_ModuleInput by lazy {
		val m = this.moduleDependency.module

		EIT_ModuleInput(m, m.getCompilation())
	}

	private val moduleDependency: NG_OutDep by lazy {
//		get() {
			/*return*/ NG_OutDep(aModuleDependency)
		}
}

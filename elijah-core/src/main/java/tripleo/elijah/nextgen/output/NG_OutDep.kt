package tripleo.elijah.nextgen.output

import tripleo.elijah.lang.i.OS_Module
import tripleo.elijah.nextgen.comp_model.CM_Module

data class NG_OutDep(
		/*@get:@JvmName("getModule")*/
		val module_: OS_Module
) : CM_Module {

	// TODO 11/04 Find how these are different
//	val filename: String by lazy { module.getFileName() }
//	val filename: String = module.getFileName()
//	fun getFilename(): String = module.getFileName()
//	val filename_: String
//		get() = module_.getFileName()
/*
	val filename_: String
		get() = module_.getFileName()
*/


//	companion object : CM_Module {
		override fun getFilename(): String = module_.fileName
		override fun getModule(): OS_Module = module_
//	}

}

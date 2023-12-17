package tripleo.elijah.comp.internal

import org.jetbrains.annotations.Contract

import tripleo.elijah.ci.i.CompilerInstructions
import tripleo.elijah.comp.i.*
import tripleo.elijah.comp.i.ICompilationBus.*

import tripleo.elijah.util.Mode
import tripleo.elijah.util.Ok
import tripleo.elijah.util.Operation

//import javax.swing.text.html.HTML.Tag.P

internal class CB_FindStdLibAction(private val ce: CompilationEnclosure, private val crState: CR_State) : CB_Action {
	private var findStdLib: CD_FindStdLib? = null

	private val o: MutableList<CB_OutputString> = ArrayList() // FIXME 07/01 how is this modified?

	init {
		//findStdLib =
		val op = obtain()
		logProgress(Prov.obtain, op)
	}

	private fun obtain(): Operation<CompilerDriven> {
		val x = ce.compilationDriver[Compilation.CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2]
		if (x.mode() == Mode.SUCCESS) {
			findStdLib = x.success() as CD_FindStdLib
		}
		return x
	}

	override fun execute() {
		logProgress(Prov.execute_begin, null)

		val preludeName = Compilation.CompilationAlways.defaultPrelude()
		if (findStdLib != null) {
			val op = findStdLib!!
				.findStdLib(crState, preludeName)
					{ oci: Operation<CompilerInstructions> -> getPushItem(oci) }

			logProgress(Prov.find_stdlib, op)
			when (op.mode()) {
				Mode.SUCCESS -> {
				}

				Mode.FAILURE -> {
				}

				else -> throw IllegalStateException("Unexpected value: " + op.mode())
			}
		}

		logProgress(Prov.execute_end, null)
	}

	private fun getPushItem(oci: Operation<CompilerInstructions>) {
		logProgress(Prov.get_push_item, oci)

		if (oci.mode() == Mode.SUCCESS) {
			val c = ce.compilation

			// README 09/09 not double
			//c.pushItem(oci.success());
			c.use(oci.success(), true)
		} else {
			throw IllegalStateException(oci.failure())
		}
	}

	private fun logProgress(code: Prov, o: Any?) {
		val name = name()
		when (code) {
			Prov.find_stdlib -> {
//				when o {
//					is Operation<Ok> {}
//				}
				val op = o as Operation<Ok>
				val text = op.toString()
				val os: CB_OutputString = COutputString(text)
				outputStrings().add(os)
			}
			Prov.get_push_item -> {
				val op = o as Operation<CompilerInstructions>
				op ?: throw NullPointerException("Expression 'yy' must not be null")
			}
			Prov.obtain -> {
				val op = o as Operation<CompilerDriven> //Operation<CompilerInstructions>
				val text = op.toString()
				val os: CB_OutputString = COutputString(text)
				outputStrings().add(os)
			}

//			else -> {
//				throw IllegalStateException("Unexpected value: $code")
//			}
			Prov.execute_end -> {
				// TODO 11/12 say something
			}
			Prov.execute_begin -> {
				// TODO 11/12 say something
			}
		}
	}

	enum class Prov {
		obtain,
		find_stdlib,
		get_push_item,
		execute_end,
		execute_begin
	}

	@Contract(pure = true)
	override fun name(): String = "find std lib"

	@Contract(pure = true)
	override fun outputStrings(): MutableList<CB_OutputString> = o
}

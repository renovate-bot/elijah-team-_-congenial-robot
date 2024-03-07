package tripleo.elijah.nextgen.outputstatement

import java.util.function.Supplier

class ReasonedStringListStatement : EG_Statement {
	override fun getExplanation(): EX_Explanation = explanation

	override fun getText(): String {
		// TODO technically speaking, this is a Flow
		val sb2 = StringBuilder()
		rss.forEach {
			sb2.append(it.text()) // TODO more, learn kotlin
		}
		return sb2.toString()
	}

	fun append(aText: String, aReason: String) {
		rss.add(ReasonedString(aText, aReason))
	}

	fun append(aText: Supplier<String>, aReason: String) {
		rss.add(ReasonedSuppliedString(aText, aReason))
	}

	fun append(aText: EG_Statement, aReason: String) {
		rss.add(ReasonedStatementString(aText, aReason))
	}

	private val explanation: EX_Explanation = EX_Explanation.withMessage("ResonedStringListStatement")
	private val rss: MutableList<IReasonedString> = ArrayList()
}

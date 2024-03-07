package tripleo.elijah.nextgen.outputstatement

data /*internal*/ class ReasonedString(
	  var text: String
	, var reason: String
) : IReasonedString {
	override fun text(): String = text
	override fun reason(): String = reason
}

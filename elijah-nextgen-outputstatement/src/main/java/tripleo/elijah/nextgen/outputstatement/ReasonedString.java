package tripleo.elijah.nextgen.outputstatement;

class ReasonedString implements IReasonedString {
	String text;
	String reason;

	public ReasonedString(final String aText, final String aReason) {
		text   = aText;
		reason = aReason;
	}

	@Override
	public String text() {
		return text;
	}

	@Override
	public String reason() {
		return reason;
	}
}

package tripleo.elijah.nextgen.outputstatement;

public class ReasonedStatementString implements IReasonedString {
	private final EG_Statement text;
	private final String reason;

	public ReasonedStatementString(final EG_Statement aText, final String aReason) {
		text = aText;
		reason = aReason;
	}

	@Override
	public String text() {
		return text.getText();
	}

	@Override
	public String reason() {
		return reason;
	}
}

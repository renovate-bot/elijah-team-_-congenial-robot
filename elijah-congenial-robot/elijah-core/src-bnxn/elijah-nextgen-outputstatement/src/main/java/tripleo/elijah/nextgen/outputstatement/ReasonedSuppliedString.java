package tripleo.elijah.nextgen.outputstatement;

import java.util.function.Supplier;

class ReasonedSuppliedString implements IReasonedString {
	Supplier<String> textSupplier;
	String           reason;

	public ReasonedSuppliedString(final Supplier<String> aText, final String aReason) {
		textSupplier = aText;
		reason       = aReason;
	}

	@Override
	public String text() {
		return textSupplier.get();
	}

	@Override
	public String reason() {
		return reason;
	}
}

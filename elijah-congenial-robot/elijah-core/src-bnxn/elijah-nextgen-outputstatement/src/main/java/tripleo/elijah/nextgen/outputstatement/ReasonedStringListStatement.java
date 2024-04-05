package tripleo.elijah.nextgen.outputstatement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ReasonedStringListStatement implements EG_Statement {
	@Override
	public EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("xyz");
	}

	@Override
	public String getText() {
		final StringBuilder sb2 = new StringBuilder();
		for (IReasonedString reasonedString : rss) {
			sb2.append(reasonedString.text());
		}
		return sb2.toString();
	}

	public void append(final String aText, final String aReason) {
		rss.add(new ReasonedString(aText, aReason));
	}

	public void append(final Supplier<String> aText, final String aReason) {
		rss.add(new ReasonedSuppliedString(aText, aReason));
	}

	public void append(final EG_Statement aText, final String aReason) {
		rss.add(new ReasonedStatementString(aText, aReason));
	}

	private final List<IReasonedString> rss = new ArrayList<>();
}

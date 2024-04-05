package tripleo.elijah_durable_congenial.nextgen.outputstatement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;

public class EX_SingleElementExplanation implements EX_Explanation {

	private final @NotNull OS_Element _element;

	@Contract(pure = true)
	public EX_SingleElementExplanation(final @NotNull OS_Element aElement) {
		_element = aElement;
	}

	public @NotNull OS_Element getElement() {
		return _element;
	}

	@Override
	public @NotNull String message() {
		return "EX_SingleElementExplanation";
	}
}

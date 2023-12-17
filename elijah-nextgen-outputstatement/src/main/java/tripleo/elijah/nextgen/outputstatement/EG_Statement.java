package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tripleo Nova
 */
public interface EG_Statement {
	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull EG_Statement of(@NotNull String aText, @NotNull EX_Explanation aExplanation) {
		return new EG_SingleStatement(aText, aExplanation);
	}

	EX_Explanation getExplanation();

	String getText();
}

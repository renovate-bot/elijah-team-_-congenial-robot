package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tripleo Nova
 */
public class EG_CompoundStatement implements EG_Statement {

	private final @NotNull EG_SingleStatement beginning;
	private final @NotNull EG_SingleStatement ending;
	private final @NotNull EX_Explanation     explanation;
	private final          boolean            indent;
	private final @NotNull EG_Statement       middle;

	@Contract(pure = true)
	public EG_CompoundStatement(final @NotNull EG_SingleStatement aBeginning,
								final @NotNull EG_SingleStatement aEnding,
								final @NotNull EG_Statement aMiddle,
								final boolean aIndent,
								final @NotNull EX_Explanation aExplanation) {
		beginning   = aBeginning;
		ending      = aEnding;
		middle      = aMiddle;
		indent      = aIndent;
		explanation = aExplanation;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return explanation;
	}

	@Override
	public @NotNull String getText() {

		final String sb = beginning.getText() +
				middle.getText() +
				ending.getText();

		return sb;
	}
}

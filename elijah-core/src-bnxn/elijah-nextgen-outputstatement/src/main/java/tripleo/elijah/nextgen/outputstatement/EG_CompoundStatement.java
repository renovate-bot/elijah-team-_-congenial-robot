package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Tripleo Nova
 */
public class EG_CompoundStatement implements EG_Statement {
	private final @NotNull EG_Statement   beginning;
	private final @NotNull EG_Statement   ending;
	private final @NotNull EX_Explanation explanation;
	private final          boolean        indent;
	private final @NotNull EG_Statement   middle;

	@Contract(pure = true)
	public EG_CompoundStatement(final @NotNull EG_Statement aBeginning,
								final @NotNull EG_Statement aEnding,
								final @NotNull EG_Statement aMiddle,
								final boolean aIndent,
								final @NotNull EX_Explanation aExplanation) {
		beginning   = aBeginning;
		ending      = aEnding;
		middle      = aMiddle;
		indent      = aIndent;
		explanation = aExplanation;
	}

	public EG_CompoundStatement(final @NotNull EG_Statement aBeginning,
								final @NotNull EG_Statement aEnding,
								final List<EG_Statement> aStatementList,
								final boolean aIndent,
								final @NotNull EX_Explanation aExplanation) {
		beginning   = aBeginning;
		ending      = aEnding;
		middle      = new EG_SequenceStatement(new EG_Naming("<<compound>>"), aStatementList);
		indent      = aIndent;
		explanation = aExplanation;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return explanation;
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(beginning.getText());

		if (indent) {
			// TODO 11/16 write a test for this
			final String middleText = middle.getText();

			for (String s : middleText.split("\n")) {
				stringBuilder.append("\t");
				stringBuilder.append(s);
				stringBuilder.append("\n");
			}
		} else {
			stringBuilder.append(middle.getText());
		}

		stringBuilder.append(ending.getText());

		return stringBuilder.toString();
	}
}

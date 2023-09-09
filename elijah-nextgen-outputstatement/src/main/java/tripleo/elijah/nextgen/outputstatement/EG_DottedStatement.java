package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EG_DottedStatement implements EG_Statement {
	private final EX_Explanation explanation;
	private final String         separator;
	private final List<String>   stringList;

	public EG_DottedStatement(final String aSeparator, final List<String> aStringList, final EX_Explanation aExplanation) {
		separator   = aSeparator;
		stringList  = aStringList;
		explanation = aExplanation;
	}

	@Override
	public EX_Explanation getExplanation() {
		return explanation;
	}

	@Override
	public @NotNull String getText() {
		return __.String_join(separator, stringList);
	}
}

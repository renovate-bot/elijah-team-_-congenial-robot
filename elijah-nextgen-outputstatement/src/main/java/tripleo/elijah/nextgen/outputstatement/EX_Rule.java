package tripleo.elijah.nextgen.outputstatement;

public class EX_Rule implements EX_Explanation {
	private final String rule;

	public EX_Rule(final String aRule) {
		rule = aRule;
	}

	@Override
	public String message() {
		return rule;
	}
}

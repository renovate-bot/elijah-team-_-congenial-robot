package tripleo.elijah.typinf;

public class IntConstant_AST extends AstNode {
	private final int value;

	IntConstant_AST(int aValue) {
		value = aValue;
	}

	public IntConstant_AST(String aValue) {
		value = Integer.valueOf(aValue);
	}

	@Override
	public String toString() {
		return "" + value;
	}
}

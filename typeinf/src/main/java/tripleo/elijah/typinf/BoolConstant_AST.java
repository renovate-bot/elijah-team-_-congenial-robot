package tripleo.elijah.typinf;

public class BoolConstant_AST extends AstNode {
	private final boolean value;

	BoolConstant_AST(boolean aValue) {
		value = aValue;
	}

	public BoolConstant_AST(String aValue) {
		value = Boolean.valueOf(aValue);
	}

	@Override
	public String toString() {
		return "" + value;
	}
}

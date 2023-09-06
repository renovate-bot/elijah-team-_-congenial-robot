package tripleo.elijah.typinf;

public class Identifier_AST extends AstNode {
	final String name;

	public Identifier_AST(String aName) {
		name = aName;
	}

	@Override
	public String toString() {
		return name;
	}
}

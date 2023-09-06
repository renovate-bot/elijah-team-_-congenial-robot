package tripleo.elijah.typinf;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Declaration mapping name = expr.
 * <p>
 * For functions expr is a Lambda node.
 */
public class Decl_AST extends AstNode {
	public AstNode expr;
	String name;

	public Decl_AST(String aName, AstNode aExpr) {
		name = aName;
		expr = aExpr;
		set_children(List_of(expr));
	}

	@Override
	public String toString() {
		return String.format("Decl(%s, %s)", name, expr);
	}
}

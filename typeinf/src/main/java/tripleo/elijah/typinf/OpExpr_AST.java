package tripleo.elijah.typinf;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Binary operation between expressions.
 */
public class OpExpr_AST extends AstNode {
	public final AstNode left;
	public final AstNode right;
	final        String  op;

	public OpExpr_AST(String op, AstNode left, AstNode right) {
		this.op    = op;
		this.left  = left;
		this.right = right;
		this.set_children(List_of(this.left, this.right));
	}

	@Override
	public String toString() {
		return String.format("(%s %s %s)", left, op, right);
	}
}

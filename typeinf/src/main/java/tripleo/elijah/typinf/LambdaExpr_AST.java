package tripleo.elijah.typinf;

import tripleo.elijah.util.Helpers;

import java.util.HashMap;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * lambda [args] -> expr
 */
public class LambdaExpr_AST extends AstNode {
	public final AstNode      expr;
	final        List<String> argnames;
	/**
	 * Used by the type inference algorithm to map discovered types for the
	 * arguments of the lambda. Since we list arguments as names (strings) and
	 * not ASTNodes, we can't keep their _type on the node.
	 */
	HashMap<String, Type> _arg_types = null;

	public LambdaExpr_AST(List<String> argnames, AstNode expr) {
		this.argnames = argnames;
		this.expr     = expr;
		this.set_children(List_of(expr));
	}

	@Override
	public String toString() {
		return String.format("Lambda([%s], %s)", Helpers.String_join(", ", argnames), expr);
	}
}

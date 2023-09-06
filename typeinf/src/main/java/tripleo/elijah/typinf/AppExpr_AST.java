package tripleo.elijah.typinf;

import tripleo.elijah.util.Helpers;

import java.util.List;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Application of a function to a sequence of arguments.
 * <p>
 * func is a node, args is a sequence of nodes.
 */
public class AppExpr_AST extends AstNode {
	public final AstNode       func;
	public final List<AstNode> args;

	public AppExpr_AST(AstNode func, List<AstNode> args) {
		this.func = func;
		this.args = args;
		this.set_children(List_of(this.func));
		this.get_children().addAll(args);
	}

	@Override
	public String toString() {
		return String.format("App(%s, [%s])", func,
							 Helpers.String_join(", ", args
									 .stream()
									 .map(a -> a.toString())
									 .collect(Collectors.toList())));
	}
}

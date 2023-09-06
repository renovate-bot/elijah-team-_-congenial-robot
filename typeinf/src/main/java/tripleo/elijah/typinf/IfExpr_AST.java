package tripleo.elijah.typinf;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * if ... then ... else ... expression.
 */
public class IfExpr_AST extends AstNode {
	final AstNode ifexpr;
	final AstNode thenexpr;
	final AstNode elseexpr;

	public IfExpr_AST(AstNode ifexpr, AstNode thenexpr, AstNode elseexpr) {
		this.ifexpr   = ifexpr;
		this.thenexpr = thenexpr;
		this.elseexpr = elseexpr;
		this.set_children(List_of(this.ifexpr, this.thenexpr, this.elseexpr));
	}

	@Override
	public String toString() {
		return String.format("If(%s, %s, %s)", this.ifexpr, this.thenexpr, this.elseexpr);
	}
}

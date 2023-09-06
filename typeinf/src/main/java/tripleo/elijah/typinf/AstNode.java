package tripleo.elijah.typinf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AstNode {
	// Used by the type inference algorithm.
	private Type          _type     = null;
	// Used by passes that traverse the AST. Each concrete node class lists the
	// sub-nodes it has as children.
	private List<AstNode> _children = new ArrayList<>();

	/**
	 * Visit all children with a function that takes a child node.
	 */
	void visit_children(Consumer<AstNode> func) {
		for (AstNode child : get_children()) {
			func.accept(child);
		}
	}

	public List<AstNode> get_children() {
		return _children;
	}

	public void set_children(List<AstNode> a_children) {
		_children = a_children;
	}

	public Type get_type() {
		return _type;
	}

	public void set_type(Type a_type) {
		_type = a_type;
	}
}

package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.util.Operation;

import java.util.ArrayList;
import java.util.List;

public class EIT_InputTree {
	private final List<_Node> nodes = new ArrayList<>();

	public void addNode(final CompilerInput aInput) {
		next_node().setCompilerInput(aInput);
	}

	private @NotNull _Node next_node() {
		var R = new _Node();
		nodes.add(R);
		return R;
	}

	public void setNodeOperation(final CompilerInput input, final Operation<?> operation) {
		for (_Node node : nodes) {
			if (node.getCompilerInput() == input) {
				node.operation = operation;
			}
		}
	}

	class _Node {
		public  Operation<?>  operation;
		private CompilerInput compilerInput;

		public CompilerInput getCompilerInput() {
			return compilerInput;
		}

		public void setCompilerInput(final CompilerInput aCompilerInput) {
			compilerInput = aCompilerInput;
		}
	}
}

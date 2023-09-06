package tripleo.elijah.stages.deduce;

import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.lang.i.OS_Element;

public record DeduceElementWrapper(OS_Element element) {
	public boolean isNamespaceStatement() {
		return element instanceof NamespaceStatement;
	}
}

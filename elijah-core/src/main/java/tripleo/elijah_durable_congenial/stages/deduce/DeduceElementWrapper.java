package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;

public record DeduceElementWrapper(OS_Element element) {
	public boolean isNamespaceStatement() {
		return element instanceof NamespaceStatement;
	}
}

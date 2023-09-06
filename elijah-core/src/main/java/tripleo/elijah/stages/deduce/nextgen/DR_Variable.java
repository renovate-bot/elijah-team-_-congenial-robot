package tripleo.elijah.stages.deduce.nextgen;

import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;

public class DR_Variable implements DR_Item {
	private final VariableStatement element;
	private final BaseEvaFunction   baseEvaFunction;

	public DR_Variable(final VariableStatement aElement, final BaseEvaFunction aBaseEvaFunction) {
		element         = aElement;
		baseEvaFunction = aBaseEvaFunction;
	}

	public boolean declaredTypeIsEmpty() {
		return element.typeName().isNull();
	}
}

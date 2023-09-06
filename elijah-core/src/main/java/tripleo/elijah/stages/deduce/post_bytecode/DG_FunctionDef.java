package tripleo.elijah.stages.deduce.post_bytecode;

import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element;

public class DG_FunctionDef implements DG_Item {
	private final FunctionDef functionDef;

	public DG_FunctionDef(final FunctionDef aFunctionDef) {
		functionDef = aFunctionDef;
	}

	public OS_Element getFunctionDef() {
		return functionDef;
	}
}

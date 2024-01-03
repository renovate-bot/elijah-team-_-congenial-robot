package tripleo.elijah.world.i;

import tripleo.elijah.comp.functionality.f291.AmazingPart;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;

public interface LivingFunction {
	int getCode();

	FunctionDef getElement();

	void offer(AmazingPart aAp);

	BaseEvaFunction evaNode();
}

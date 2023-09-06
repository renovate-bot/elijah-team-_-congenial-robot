package tripleo.elijah.world.i;

import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.gen_fn.EvaClass;

public interface LivingClass extends LivingNode {
	EvaClass evaNode();

	int getCode();

	ClassStatement getElement();

	GarishClass getGarish();

	//void setGarish(GarishClass aGarishClass);
}

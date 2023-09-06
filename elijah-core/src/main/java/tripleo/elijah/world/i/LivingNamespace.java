package tripleo.elijah.world.i;

import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

public interface LivingNamespace extends LivingNode {
	EvaNamespace evaNode();

	void garish(GenerateC aGenerateC, GenerateResult aGr, GenerateResultSink aResultSink);

	int getCode();

	NamespaceStatement getElement();

	GarishNamespace getGarish();

	//void setGarish(GarishClass aGarishClass);
}

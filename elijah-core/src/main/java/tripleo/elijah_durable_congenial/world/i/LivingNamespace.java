package tripleo.elijah_durable_congenial.world.i;

import tripleo.elijah_durable_congenial.comp.functionality.f291.AmazingPart;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.garish.GarishNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_durable_congenial.comp.functionality.f291.AmazingPart;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.garish.GarishNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl.GenerateResultSink;

public interface LivingNamespace extends LivingNode {
	EvaNamespace evaNode();

	void garish(GenerateC aGenerateC, GenerateResult aGr, GenerateResultSink aResultSink);

	int getCode();

	NamespaceStatement getElement();

	GarishNamespace getGarish();

	void offer(AmazingPart aAp);

	//void setGarish(GarishClass aGarishClass);
}

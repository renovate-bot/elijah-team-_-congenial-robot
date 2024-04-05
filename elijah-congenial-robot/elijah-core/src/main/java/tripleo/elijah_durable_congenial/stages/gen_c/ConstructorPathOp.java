package tripleo.elijah_durable_congenial.stages.gen_c;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;

interface ConstructorPathOp {
	@Nullable String getCtorName();

	@Nullable EvaNode getResolved();
}

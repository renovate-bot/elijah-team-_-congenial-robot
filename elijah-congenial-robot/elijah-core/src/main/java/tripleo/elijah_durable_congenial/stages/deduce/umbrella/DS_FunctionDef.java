package tripleo.elijah_durable_congenial.stages.deduce.umbrella;

import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;

/**
 * This is a callback for resolveWith.
 * <p>
 * It should be prepared with a {@link DS_Rider}
 * </p>
 */
public interface DS_FunctionDef extends DS_Base {
	void accept(FunctionDef fd);
	void accept(BaseEvaFunction gf);
	void accept(FunctionInvocation aFunctionInvocation);
}

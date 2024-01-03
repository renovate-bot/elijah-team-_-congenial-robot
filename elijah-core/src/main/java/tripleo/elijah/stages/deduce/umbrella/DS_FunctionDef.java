package tripleo.elijah.stages.deduce.umbrella;

import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;

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

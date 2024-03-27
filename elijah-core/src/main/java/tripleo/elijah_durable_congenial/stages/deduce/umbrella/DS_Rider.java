package tripleo.elijah_durable_congenial.stages.deduce.umbrella;

import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;

/**
 * This is a complement to {@link DS_Base} in resolveWith.<br/>
 * It is used to provide information about the environment in which/
 * for which the resolve is running.<br/>
 * <br/>
 * {@code #generatedFunction} is the function in which the name is being looked up from
 */
public interface DS_Rider {
	BaseEvaFunction generatedFunction();
}

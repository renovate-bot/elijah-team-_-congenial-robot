package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;

public record InheritedMethodCalledFromInheritee(FunctionDef activeFunction,
												 ClassStatement definedIn,
												 BaseEvaFunction calledIn)
		implements CI_Hint {
	// the function referenced by pte.expression is an inherited method
	// defined in genType.resolved and called in generatedFunction
	// for whatever reason we don't have a CodePoint (gf, instruction...)
}

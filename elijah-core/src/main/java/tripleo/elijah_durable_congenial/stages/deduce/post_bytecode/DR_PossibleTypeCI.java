package tripleo.elijah_durable_congenial.stages.deduce.post_bytecode;

import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_PossibleType;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;

public class DR_PossibleTypeCI implements DR_PossibleType {
	private final ClassInvocation    ci;
	private final FunctionInvocation fi;

	public DR_PossibleTypeCI(final ClassInvocation aCi, final FunctionInvocation aFi) {
		ci = aCi;
		fi = aFi;
	}
}

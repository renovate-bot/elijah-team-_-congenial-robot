package tripleo.elijah.stages.deduce.post_bytecode;

import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.deduce.nextgen.DR_PossibleType;

public class DR_PossibleTypeCI implements DR_PossibleType {
	private final ClassInvocation    ci;
	private final FunctionInvocation fi;

	public DR_PossibleTypeCI(final ClassInvocation aCi, final FunctionInvocation aFi) {
		ci = aCi;
		fi = aFi;
	}
}

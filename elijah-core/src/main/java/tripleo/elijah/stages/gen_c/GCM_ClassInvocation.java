package tripleo.elijah.stages.gen_c;

import tripleo.elijah.stages.deduce.ClassInvocation;

public class GCM_ClassInvocation {
	private final ClassInvocation ci;

	public GCM_ClassInvocation(final ClassInvocation aClassInvocation) {
		ci = aClassInvocation;
	}

	public GCM_CI_GenericPart genericPart() {
		return new GCM_CI_GenericPart(ci.genericPart());
	}
}

package tripleo.elijah.stages.gen_c;

import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.stages.deduce.ClassInvocation;

public class GCM_CI_GenericPart {
	private final ClassInvocation.CI_GenericPart ciGenericPart;

	public GCM_CI_GenericPart(final ClassInvocation.CI_GenericPart aCIGenericPart) {
		ciGenericPart = aCIGenericPart;
	}

	public GCM_OS_Type valueForKey(final TypeName aTn) {
		return new GCM_OS_Type(ciGenericPart.valueForKey(aTn));
	}
}

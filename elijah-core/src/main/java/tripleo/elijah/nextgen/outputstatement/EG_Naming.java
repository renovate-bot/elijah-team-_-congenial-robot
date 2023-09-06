package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.Nullable;

public class EG_Naming {
	final           String s;
	final @Nullable String s1;

	public EG_Naming(final String aS) {
		s  = aS;
		s1 = null;
	}

	public EG_Naming(final String aS, final String aS1) {
		s  = aS;
		s1 = aS1;
	}
}

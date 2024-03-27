package tripleo.elijah_congenial.gen_c.testing;

import java.util.Objects;

public class GCPA_Equality implements GCPA_Base {
	private final String s;
	private boolean result;

	public GCPA_Equality(final String aS) {
		s = aS;
	}

	@Override
	public void testing(final String aTestament) {
		this.result = Objects.equals(s, aTestament);
	}

	public boolean getResult() {
		return result;
	}
}

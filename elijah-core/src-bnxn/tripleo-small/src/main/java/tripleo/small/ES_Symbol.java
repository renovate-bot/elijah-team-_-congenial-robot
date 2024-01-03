package tripleo.small;

public class ES_Symbol implements ES_Item {
	private final String s;

	public ES_Symbol(final String aS) {
		s = aS;
	}

	public String getText() {
		return s;
	}

	@Override
	public String toString() {
		return String.format("<Symbol %s>", s);
	}
}

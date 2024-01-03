package tripleo.elijah;

public class UnintendedUseException extends RuntimeException {
	private static final long serialVersionUID = -1838486886356864300L;
	public UnintendedUseException(String string) {
		super(string);
	}
	public UnintendedUseException() {
		super();
	}
}

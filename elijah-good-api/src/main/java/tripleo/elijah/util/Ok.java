package tripleo.elijah.util;

@SuppressWarnings({"Singleton", "FinalClass"})
public final class Ok {
	@SuppressWarnings("InstantiationOfUtilityClass")
	private static final Ok INSTANCE = new Ok();

	private Ok() {
	}

	public static Ok instance() {
		return INSTANCE;
	}
}

package tripleo.elijah.sanaa;

import org.jetbrains.annotations.NotNull;

public enum ElIntrinsics { ;
	public static <T> void checkNotNullParameter(@NotNull Object a, T vut) {
		assert vut != null;
	}

	public static <T> void checkNotNullExpressionValue(@NotNull Object a, T vut) {
		assert vut != null;
	}

	public static <T> void checkNotNull(@NotNull Object a, T vut) {
		assert vut != null;
	}

	public static <T> void checkNotNull(T vut) {
		assert vut != null;
	}

	public static <T> boolean areEqual(@NotNull T module, @NotNull T module2) {
		if (module == module2) return true;
		return false;
	}
}

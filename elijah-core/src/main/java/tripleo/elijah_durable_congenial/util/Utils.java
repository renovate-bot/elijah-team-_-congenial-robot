package tripleo.elijah_durable_congenial.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings({"UtilityClassCanBeEnum", "FinalClass"})
public final class Utils {
	private Utils() {
	}

	public static <T> @NotNull T instantiate(final @NotNull Class<T> aClass) {
		try {
			var c = aClass.getConstructor();
			var xx = c.newInstance();
			return xx;
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException aE) {
			throw new RuntimeException(aE);
		}
	}
}

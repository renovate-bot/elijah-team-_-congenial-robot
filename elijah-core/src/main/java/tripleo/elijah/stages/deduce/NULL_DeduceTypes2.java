package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Deprecated
public final class NULL_DeduceTypes2 implements Supplier<DeduceTypes2> {
	@Override
	@Contract(pure = true)
	public @Nullable DeduceTypes2 get() {
		return null;
	}
}

package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record RegisterClassInvocation_env(
		@NotNull ClassInvocation ci,
		@Nullable DeduceTypes2 deduceTypes2,
		@Nullable DeducePhase phase) {
}

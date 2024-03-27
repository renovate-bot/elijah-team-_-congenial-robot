package tripleo.elijah.stateful;

import org.jetbrains.annotations.NotNull;

public interface Stateful {
	void mvState(State aO, @NotNull State aState);

	void setState(final State aState);
}

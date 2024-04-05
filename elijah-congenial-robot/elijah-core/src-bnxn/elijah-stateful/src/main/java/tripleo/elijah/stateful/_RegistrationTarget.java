package tripleo.elijah.stateful;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class _RegistrationTarget {
	public State registerState(final @NotNull State aState) {
		if (!(registeredStates.contains(aState))) {
			registeredStates.add(aState);

			final int id = registeredStates.indexOf(aState);

			aState.setIdentity(new StateRegistrationToken(id));
			return aState;
		}

		return aState;
	}

	private final List<State> registeredStates = new ArrayList<>();
}

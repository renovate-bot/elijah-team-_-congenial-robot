package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.stateful.State;
import tripleo.elijah.stateful.StateRegistrationToken;
import tripleo.elijah.stateful.Stateful;

public interface IStateRunnable extends Stateful {
	void run();

	enum ST {
		;
		public static State EXIT_RUN;

		public static void register(final @NotNull DeducePhase phase) {
			EXIT_RUN = phase.registerState(new ExitRunState());
		}

		private static class ExitRunState implements State {
			private boolean runAlready;
			private StateRegistrationToken identity;

			@Override
			public void apply(final @NotNull DefaultStateful element) {
//                              boolean b = ((StatefulBool) element).getValue();
				if (!runAlready) {
					runAlready = true;
					((StatefulRunnable) element).run();
				}
			}

			@Override
			public boolean checkState(final DefaultStateful aElement3) {
				return true;
			}

			@Override
			public void setIdentity(final StateRegistrationToken aId) {
				this.identity = aId;
			}

		}

	}
}
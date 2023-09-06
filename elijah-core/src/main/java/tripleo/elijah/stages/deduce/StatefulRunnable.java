package tripleo.elijah.stages.deduce;

import tripleo.elijah.stateful.DefaultStateful;

class StatefulRunnable extends DefaultStateful implements IStateRunnable {
	private final Runnable runnable;

	public StatefulRunnable(final Runnable aRunnable) {
		runnable = aRunnable;
	}

	@Override
	public void run() {
		runnable.run();
	}
}

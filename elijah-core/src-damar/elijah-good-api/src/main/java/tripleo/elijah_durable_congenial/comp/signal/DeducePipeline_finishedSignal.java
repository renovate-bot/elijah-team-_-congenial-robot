package tripleo.elijah_durable_congenial.comp.signal;

import tripleo.elijah_durable_congenial.comp.i.SimpleSignal;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;

public enum DeducePipeline_finishedSignal implements SimpleSignal {
	INSTANCE; // everywhere!!

	private boolean runCalled=false;

	@Override
	public String getListenerCode() {
		return "cd833a2f-2c6b-4ccf-9849-93f6a999ae3b";
	}

	@Override
	public void run(final CompilationEnclosure ce) {
		runCalled = true;
		System.err.println("&&&&& DeducePipeline finished");
	}

	@Override
	public String identityString() {
		return "DeducePipeline::finishedSignal";
	}

	@Override
	public Object getSignalResult() {
		return runCalled;
	}
}

package tripleo.elijah_durable_congenial.comp.i;

public interface SimpleSignal {
	String getListenerCode();

	void run(CompilationEnclosure ce);

	String identityString();

	Object getSignalResult();
}

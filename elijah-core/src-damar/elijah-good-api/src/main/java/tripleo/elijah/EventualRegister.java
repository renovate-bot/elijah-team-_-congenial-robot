package tripleo.elijah;

public interface EventualRegister {
	<P> void register(Eventual<P> e);

	void checkFinishEventuals();
}

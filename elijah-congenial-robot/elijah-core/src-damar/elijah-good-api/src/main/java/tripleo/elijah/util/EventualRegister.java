package tripleo.elijah.util;

public interface EventualRegister {
	<P> void register(Eventual<P> e);

	void checkFinishEventuals();
}

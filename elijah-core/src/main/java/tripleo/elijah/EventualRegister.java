package tripleo.elijah;

public interface EventualRegister {
	public <P> void register(Eventual<P> e);

	public void check(); // TODO signature
}

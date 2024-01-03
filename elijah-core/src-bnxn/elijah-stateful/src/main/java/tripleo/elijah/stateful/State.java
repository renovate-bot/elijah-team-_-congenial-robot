package tripleo.elijah.stateful;

public interface State {
	void apply(DefaultStateful element);

	boolean checkState(DefaultStateful aElement3);

	void setIdentity(StateRegistrationToken aId);
}

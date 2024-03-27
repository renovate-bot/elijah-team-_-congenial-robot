package tripleo.elijah_congenial.pipelines.write;

public interface WP_State_Control {
	void clear();

	void exception(final Exception e);

	Exception getException();

	boolean hasException();
}

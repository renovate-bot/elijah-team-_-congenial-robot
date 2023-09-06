package tripleo.elijah.stages.write_stage.pipeline_impl;

public interface WP_State_Control {
	void clear();

	void exception(final Exception e);

	Exception getException();

	boolean hasException();
}

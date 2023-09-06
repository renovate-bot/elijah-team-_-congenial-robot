package tripleo.elijah.stages.write_stage.pipeline_impl;

public interface WP_Indiviual_Step {
	void act(final WritePipelineSharedState st, final WP_State_Control sc);
}

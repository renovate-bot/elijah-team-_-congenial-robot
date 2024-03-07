package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah_congenial.pipelines.deduce.DeducePipelineImpl;

/**
 * Created 8/21/21 10:10 PM
 */
public class DeducePipeline implements PipelineMember {
	private final DeducePipelineImpl impl;

	public DeducePipeline(final IPipelineAccess aPipelineAccess) {
		impl = new DeducePipelineImpl(aPipelineAccess);
	}

	// NOTES 23/11/10
	//  1. #createWorldModule is only created here
	//    - this is contrary to other branches where there are more than one location
	//  2. mcp is a bit involved
	//  3. We loop modules
	@Override
	public void run(final @NotNull CR_State aSt, final CB_Output aOutput) {
		impl.run(aSt, aOutput);
	}
}

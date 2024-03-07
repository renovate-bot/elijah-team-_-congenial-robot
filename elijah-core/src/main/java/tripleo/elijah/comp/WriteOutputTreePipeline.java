package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah_congenial.pipelines.write_output_tree.WriteOutputTreePipelineImpl;
import tripleo.elijah_congenial.pipelines.write_output_tree.WriteOutputTreePipelineImpl_IPipelineAccess;

public class WriteOutputTreePipeline implements PipelineMember {
	private final WriteOutputTreePipelineImpl impl;

	public WriteOutputTreePipeline(final IPipelineAccess aPipelineAccess) {
		impl = new WriteOutputTreePipelineImpl(new WriteOutputTreePipelineImpl_IPipelineAccess(){});
	}

	@Override
	public void run(final @NotNull CR_State st, final CB_Output aOutput) throws Exception {
		impl.run(st, aOutput);
	}
}

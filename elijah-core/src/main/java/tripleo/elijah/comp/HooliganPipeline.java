package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.stages.hooligan.pipeline_impl.HooliganPipelineImpl;

public class HooliganPipeline implements PipelineMember {
	private final @NotNull IPipelineAccess      pa;
	private final          HooliganPipelineImpl i = new HooliganPipelineImpl();

	@Contract(pure = true)
	public HooliganPipeline(@NotNull IPipelineAccess pa0) {
		pa = pa0;
	}

	@Override
	public void run(final CR_State aSt, final CB_Output aOutput) throws Exception {
		try {
			final Compilation compilation = pa.getCompilation();
			i.run(compilation);
		} catch (Throwable t) {
			t.printStackTrace();
			int y = 2;
			y = y;
		}
	}
}

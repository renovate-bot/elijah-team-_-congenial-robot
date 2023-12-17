package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.output.NG_OutputNamespace;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaNamespace;

class AmazingNamespace implements Amazing {
	private final OS_Module                        mod;
	private final Compilation                      compilation;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final EvaNamespace                     n;

	public AmazingNamespace(final @NotNull EvaNamespace n,
							final WPIS_GenerateOutputs.OutputItems itms,
							final IPipelineAccess aPa) {
		this.n              = n;
		mod                 = n.module();
		compilation         = mod.getCompilation();
		this.itms           = itms;
	}

	void waitGenC(final GenerateC ggc) {
		var on = new NG_OutputNamespace();
		on.setNamespace(compilation.livingRepo().getNamespace(n).getGarish(), ggc);
		itms.addItem(on);
	}

	public OS_Module mod() {
		return mod;
	}
}

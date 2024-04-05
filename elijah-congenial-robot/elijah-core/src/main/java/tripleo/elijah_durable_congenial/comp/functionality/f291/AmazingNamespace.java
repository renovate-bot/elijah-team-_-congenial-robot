package tripleo.elijah_durable_congenial.comp.functionality.f291;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.IPipelineAccess;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.output.NG_OutputNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;

class AmazingNamespace implements Amazing {
	private final OS_Module    mod;
	private final Compilation  compilation;
	private final OutputItems  itms;
	private final EvaNamespace n;

	public AmazingNamespace(final @NotNull EvaNamespace n,
							final OutputItems itms,
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

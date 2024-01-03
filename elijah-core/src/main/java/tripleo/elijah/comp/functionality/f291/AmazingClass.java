package tripleo.elijah.comp.functionality.f291;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.output.NG_OutputClass;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingRepo;

class AmazingClass implements Amazing {
	private final OS_Module            mod;
	private final Compilation          compilation;
	private final OutputItems          itms;
	private final EvaClass             c;
	private final CompilationEnclosure ce;

	public AmazingClass(final @NotNull EvaClass aEvaClass,
						final @NotNull OutputItems aOutputItems,
						final CompilationEnclosure aCompilationEnclosure) {
		this.c      = aEvaClass;
		mod         = aEvaClass.module();
		compilation = aCompilationEnclosure.getCompilation();
		ce          = aCompilationEnclosure;
		itms        = aOutputItems;
	}

	void waitGenC(final GenerateC ggc) {
		final NG_OutputClass oc         = new NG_OutputClass();
		final LivingRepo     livingRepo = compilation.livingRepo();

		//ce.world
		final LivingClass    aClass     = livingRepo.getClass(c);
		final GarishClass    garish     = aClass.getGarish();

		oc.setClass(garish, ggc);
		itms.addItem(oc);
	}

	public OS_Module mod() {
		return mod;
	}
}

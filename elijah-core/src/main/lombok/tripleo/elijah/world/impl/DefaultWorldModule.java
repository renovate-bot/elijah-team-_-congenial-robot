package tripleo.elijah.world.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.comp.notation.GN_PL_Run2;
import tripleo.elijah_durable_congenial.comp.notation.GN_PL_Run2.GenerateFunctionsRequest;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_durable_congenial.stages.inter.ModuleThing;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

public class DefaultWorldModule implements WorldModule {
	private final Eventual<GeneratedClasses> _p_GeneratedClasses = new Eventual<>();
	private final OS_Module                  mod;
	private       ModuleThing                            thing;
	private       GN_PL_Run2.GenerateFunctionsRequest    rq;

	public DefaultWorldModule(final OS_Module aMod, final @NotNull CompilationEnclosure ce) {
		mod = aMod;
		final ModuleThing mt = ce.addModuleThing(mod);
		setThing(mt);
	}

	private void setThing(ModuleThing mt) {
		// antilombok
		this.thing = mt;
	}

	@Override
	public OS_Module module() {
		return mod;
	}

	@Override
	public EIT_ModuleInput input() {
		//return null;
		throw new UnintendedUseException("24j3 not implemented");
	}

	@Override
	public GN_PL_Run2.GenerateFunctionsRequest rq() {
		return rq;
	}

	@Override
	public Eventual<GeneratedClasses> getEventual() {
		return _p_GeneratedClasses;
	}

	public ModuleThing thing() {
		return thing;
	}

	public void setRq(GenerateFunctionsRequest rq2) {
		// antilombok
		rq = rq2;
	}
}

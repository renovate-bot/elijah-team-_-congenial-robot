package tripleo.elijah.world.impl;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.comp.notation.GN_PL_Run2.GenerateFunctionsRequest;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.world.i.WorldModule;

public class DefaultWorldModule implements WorldModule {
	private final Eventual<DeducePhase.GeneratedClasses> _p_GeneratedClasses = new Eventual<>();
	private final OS_Module   mod;
	@Setter
	private       ModuleThing thing;
	@Setter @Getter
	private GN_PL_Run2.GenerateFunctionsRequest rq;

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
	public Eventual<DeducePhase.GeneratedClasses> getEventual() {
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

package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah.nextgen.model.SM_Module;

import tripleo.elijah.nextgen.model.impl.SM_Module_;

public class EIT_ModuleInput implements EIT_Input {
	private final Compilation c;
	private final OS_Module   module;

	@Contract(pure = true)
	public EIT_ModuleInput(final OS_Module aModule, final Compilation aC) {
		module = aModule;
		c      = aC;
	}

	public @NotNull SM_Module computeSourceModel() {
		final SM_Module sm = new SM_Module_(/*this*/);
		return sm;
	}

	@Override
	public @NotNull EIT_InputType getType() {
		return EIT_InputType.ELIJAH_SOURCE;
	}

	public OS_Module module() {
		return this.module;
	}
}

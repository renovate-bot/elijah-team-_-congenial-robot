package tripleo.elijah.nextgen.comp_model;

//import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.comp.nextgen.CP_Path;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;

public interface CM_Module {
	String getFilename();

	// README 11/04 don't really like this,
	// but what are you trying to do without it?
	OS_Module getModule();

	/**
	 * {@link CompilerInput} (meh, but)
	 * {@link EIT_ModuleInput}
	 * {@link DeduceTypes2}
	 * {@link CP_Path}
	 * {@link ElijahSpec} !!
	 */
}

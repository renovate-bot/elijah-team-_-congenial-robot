package tripleo.elijah.nextgen.comp_model;

//import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah.lang.i.OS_Module;

public interface CM_Module {
	String getFilename();

	// README 23/11/04 don't really like this,
	// but what are you trying to do without it?
	OS_Module getModule();

	/**
	 * {@link tripleo.elijah.comp.CompilerInput} (meh, but)
	 * {@link tripleo.elijah.nextgen.inputtree.EIT_ModuleInput}
	 * {@link tripleo.elijah.stages.deduce.DeduceTypes2}
	 * {@link tripleo.elijah.comp.nextgen.CP_Path}
	 * {@link ElijahSpec} !!
	 */
}

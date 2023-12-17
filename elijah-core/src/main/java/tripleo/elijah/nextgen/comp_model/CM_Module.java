package tripleo.elijah.nextgen.comp_model;

import tripleo.elijah.lang.i.OS_Module;

public interface CM_Module {
	String getFilename();

	// README 11/04 don't really like this,
	// but what are you trying to do without it?
	OS_Module getModule();
}

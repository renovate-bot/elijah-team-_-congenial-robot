package tripleo.elijah.world.i;

import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;

public interface WorldModule {
	OS_Module module();

	EIT_ModuleInput input();

	GN_PL_Run2.GenerateFunctionsRequest rq();
}

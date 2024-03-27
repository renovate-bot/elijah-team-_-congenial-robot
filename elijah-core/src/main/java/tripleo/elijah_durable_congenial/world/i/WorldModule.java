package tripleo.elijah_durable_congenial.world.i;

import tripleo.elijah.Eventual;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.comp.notation.GN_PL_Run2;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;

public interface WorldModule {
	OS_Module module();

	EIT_ModuleInput input();

	GN_PL_Run2.GenerateFunctionsRequest rq();

	Eventual<GeneratedClasses> getEventual();
}

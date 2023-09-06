package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;

public class NG_OutDep {
	String    filename;
	OS_Module module;


	public NG_OutDep(final @NotNull OS_Module aModuleDependency) {
		module   = aModuleDependency;
		filename = aModuleDependency.getFileName();
	}

	public OS_Module module() {
		return module;
	}
}

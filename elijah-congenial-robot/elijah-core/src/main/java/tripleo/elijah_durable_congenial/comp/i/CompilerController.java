package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah_durable_congenial.comp.CompilerInput;

import java.util.List;

public interface CompilerController {
	void printUsage();

	void processOptions();

	void runner();

	void setInputs(Compilation aCompilation, List<CompilerInput> aInputs);
}

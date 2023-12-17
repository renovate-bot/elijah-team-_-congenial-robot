package tripleo.elijah.comp.i;

import tripleo.elijah.comp.CompilerInput;

import java.util.List;

public interface CompilerController {
	void _setInputs(Compilation aCompilation, List<CompilerInput> aInputs);

	void printUsage();

	void processOptions();

	void runner();
}

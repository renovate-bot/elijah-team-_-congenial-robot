package tripleo.elijah.comp.i;

import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.internal.CompilationBus;

import java.util.List;

@FunctionalInterface
public interface OptionsProcessor {
	//String[] process(final Compilation c, final List<String> args) throws Exception;

	String[] process(Compilation aC, List<CompilerInput> aInputs, CompilationBus aCb) throws Exception;
}

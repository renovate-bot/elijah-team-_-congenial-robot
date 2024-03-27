package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.comp.internal.CompilationBus;

import java.util.List;

@FunctionalInterface
public interface OptionsProcessor {
	//String[] process(final Compilation c, final List<String> args) throws Exception;

	String[] process(Compilation aC, List<CompilerInput> aInputs, CompilationBus aCb) throws Exception;
}

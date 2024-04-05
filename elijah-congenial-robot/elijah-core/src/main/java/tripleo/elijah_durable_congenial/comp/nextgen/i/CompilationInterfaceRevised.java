package tripleo.elijah_durable_congenial.comp.nextgen.i;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah_durable_congenial.comp.CompilerInput;

import java.util.List;

public interface CompilationInterfaceRevised {
	Pair<CompOutput, CompInteractive> compile(List<CompilerInput> lci);
}

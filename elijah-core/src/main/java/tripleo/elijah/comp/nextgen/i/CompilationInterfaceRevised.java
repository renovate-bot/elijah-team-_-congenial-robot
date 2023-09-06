package tripleo.elijah.comp.nextgen.i;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.comp.CompilerInput;

import java.util.List;

public interface CompilationInterfaceRevised {
	Pair<CompOutput, CompInteractive> compile(List<CompilerInput> lci);
}

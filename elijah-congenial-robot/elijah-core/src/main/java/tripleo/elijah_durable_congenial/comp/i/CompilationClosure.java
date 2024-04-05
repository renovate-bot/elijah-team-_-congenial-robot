package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah_durable_congenial.comp.IO;

public interface CompilationClosure {
	ErrSink errSink();

	Compilation getCompilation();

	IO io();
}

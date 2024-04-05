package tripleo.elijah_durable_congenial.comp.nextgen.i;

import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_Input;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputFile;

public interface CK_SourceFile {
	CompilerInput compilerInput();

	EIT_Input input(); // s ??

	EOT_OutputFile output(); // s ??
}

package tripleo.elijah.comp.functionality.f291;

import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;

import java.util.List;

public interface Writeable {
	String filename();

	EG_Statement statement();

	List<EIT_Input> inputs();

	EOT_FileNameProvider getFilenameProvider();
}

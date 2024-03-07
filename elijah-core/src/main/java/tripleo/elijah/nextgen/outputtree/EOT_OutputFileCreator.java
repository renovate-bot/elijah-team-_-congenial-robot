package tripleo.elijah.nextgen.outputtree;

import org.jdeferred2.DoneCallback;
import tripleo.elijah.comp.i.ICompilationAccess2;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;

import java.util.List;

public interface EOT_OutputFileCreator {
	void provideInputs(List<EIT_Input> aInputs);

	void provideFileName(EOT_FileNameProvider aFilename1);

	void provideSeq(EG_Statement aSeq);

	void provideSeq(DoneCallback<EG_Statement> aSeq);

	void provideCompilation(ICompilationAccess2 aICompilationAccess2);
}

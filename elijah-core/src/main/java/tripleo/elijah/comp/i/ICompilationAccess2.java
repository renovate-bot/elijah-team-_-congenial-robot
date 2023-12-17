package tripleo.elijah.comp.i;

import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;

public interface ICompilationAccess2 {
	EOT_OutputTree getOutputTree();

	void addCodeOutput(EOT_OutputFile.FileNameProvider aFileNameProvider, EOT_OutputFile aOutputFile);

	EOT_OutputFile createOutputFile(List<EIT_Input> aInputs,
									EOT_OutputFile.FileNameProvider aFilename,
									EOT_OutputType aEOTOutputType,
									EG_Statement aStatement);

	void addCodeOutput(EOT_OutputFile.FileNameProvider aFilename, EOT_OutputFile aOutputFile, boolean addFlag);

	WorldModule createWorldModule(OS_Module aMod);

	LivingRepo world();
}

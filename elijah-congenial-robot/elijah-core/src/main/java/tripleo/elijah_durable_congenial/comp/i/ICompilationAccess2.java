package tripleo.elijah_durable_congenial.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_Input;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah_durable_congenial.world.i.LivingRepo;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

import java.util.List;

public interface ICompilationAccess2 {
	EOT_OutputTree getOutputTree();

	void addCodeOutput(EOT_FileNameProvider aFileNameProvider, EOT_OutputFile aOutputFile);

	EOT_OutputFile createOutputFile(List<EIT_Input> aInputs,
									EOT_FileNameProvider aFilename,
									EOT_OutputType aEOTOutputType,
									EG_Statement aStatement);

	void addCodeOutput(EOT_FileNameProvider aFilename, EOT_OutputFile aOutputFile, boolean addFlag);

	WorldModule createWorldModule(OS_Module aMod);

	LivingRepo world();

	@NotNull Operation<Ok> mal_ReadEval(String string);
}

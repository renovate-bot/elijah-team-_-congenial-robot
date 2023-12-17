package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.ICompilationAccess2;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;

public class CompilationAccess2Impl implements ICompilationAccess2 {
	private final Compilation c;

	public CompilationAccess2Impl(final Compilation aC) {
		c = aC;
	}

	@Override
	public EOT_OutputTree getOutputTree() {
		return c.getOutputTree();
	}

	@Override
	public void addCodeOutput(final EOT_OutputFile.FileNameProvider aFileNameProvider, final EOT_OutputFile aOutputFile) {
		c.reports().addCodeOutput(aFileNameProvider, aOutputFile);
	}

	@Override
	public EOT_OutputFile createOutputFile(final List<EIT_Input> aInputs,
										   final EOT_OutputFile.FileNameProvider aFilename,
										   final EOT_OutputType aEOTOutputType,
										   final EG_Statement aStatement) {
		return new EOT_OutputFile(aInputs, aFilename, aEOTOutputType, aStatement);
	}

	@Override
	public void addCodeOutput(final EOT_OutputFile.FileNameProvider aFilename, final EOT_OutputFile aOutputFile, final boolean addFlag) {
		addCodeOutput(aFilename, aOutputFile);
		getOutputTree().add(aOutputFile);
	}

	@Override
	public WorldModule createWorldModule(final OS_Module aMod) {
		return this.c.con().createWorldModule(aMod);
	}

	@Override
	public LivingRepo world() {
		return this.c.world();
	}
}

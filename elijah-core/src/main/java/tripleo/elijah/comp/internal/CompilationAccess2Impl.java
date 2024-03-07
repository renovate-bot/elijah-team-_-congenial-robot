package tripleo.elijah.comp.internal;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess2;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;
import tripleo.vendor.mal.stepA_mal;
import tripleo.vendor.mal.types;

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
	public void addCodeOutput(final EOT_FileNameProvider aFileNameProvider, final EOT_OutputFile aOutputFile) {
		addCodeOutput(aFileNameProvider, aOutputFile, false);
	}

	@Override
	public void addCodeOutput(final EOT_FileNameProvider aFileNameProvider, final EOT_OutputFile aOutputFile, final boolean addFlag) {
		c.reports().addCodeOutput(aFileNameProvider, aOutputFile);
		if (addFlag) {
			getOutputTree().add(aOutputFile);
		}
	}

	@Override
	public EOT_OutputFile createOutputFile(final List<EIT_Input> aInputs,
										   final EOT_FileNameProvider aFilename,
										   final EOT_OutputType aEOTOutputType,
										   final EG_Statement aStatement) {
		return new EOT_OutputFile(aInputs, aFilename, aEOTOutputType, aStatement);
	}

	@Override
	public WorldModule createWorldModule(final OS_Module aMod) {
		return this.c.con().createWorldModule(aMod);
	}

	@Override
	public LivingRepo world() {
		return this.c.world();
	}

	@Override
	public @NotNull Operation<Ok> mal_ReadEval(String string) {
		final CompilationEnclosure ce  = this.c.getCompilationEnclosure();
		final stepA_mal.MalEnv2    env = ce.getMalBulge().getEnv();
		/*final */
		@NotNull Operation<Ok> result;
		try {
			env.re(string);
			result = Operation.success(Ok.instance());
		} catch (types.MalThrowable aE) {
			result = Operation.failure(aE);
		}
		return result;
	}

	@Override
	public EOT_OutputFileCreator createOutputFile2(final EOT_OutputType aOutputType) {
		return new EOT_OutputFileCreator() {
			private EG_Statement seq;
			private EOT_FileNameProvider fileName;
			private List<EIT_Input> inputs;

			@Override
			public void provideInputs(final List<EIT_Input> aInputs) {
				this.inputs = aInputs;
			}

			@Override
			public void provideFileName(final EOT_FileNameProvider aFileNameProvider) {
				this.fileName = aFileNameProvider;
			}

			@Override
			public void provideSeq(final EG_Statement aSeq) {
				this.seq = aSeq;
			}

			@Override
			public void provideSeq(final DoneCallback<EG_Statement> aSeq) {
				throw new UnintendedUseException("forgot how this is supposed to work.");
			}

			@Override
			public void provideCompilation(final ICompilationAccess2 aCompilationAccess2) {
				if (inputs != null
				&& fileName != null
				&& seq != null) {
					var cot = aCompilationAccess2.getOutputTree();
					var off = aCompilationAccess2.createOutputFile(inputs, fileName, aOutputType, seq);
					cot.add(off);
				} else {
					throw new IllegalStateException("something is null somewhere");
				}
			}
		};
	}
}

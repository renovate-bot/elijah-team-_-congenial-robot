package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.functionality.f291.U;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.comp.nextgen.i.CP_RootType;
import tripleo.elijah.factory.comp.NextgenFactory;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;

import java.util.List;

public class WriteOutputTreePipeline implements PipelineMember {
	private final IPipelineAccess pa;

	public WriteOutputTreePipeline(final IPipelineAccess aPipelineAccess) {
		pa = aPipelineAccess;
	}

	@Override
	public void run(final @NotNull CR_State st, final CB_Output aOutput) throws Exception {
		final Compilation    compilation = st.ca().getCompilation();
		final EOT_OutputTree ot          = compilation.getOutputTree();
		final List<EOT_OutputFile> l           = ot.getList();

		//
		//
		//
		//
		//
		//
		//
		// HACK should be done earlier in process
		//
		//
		//
		//
		//
		//
		//
		addLogs(l, compilation.pa());

		final CP_Paths paths = compilation.paths();
		paths.signalCalculateFinishParse(); // TODO maybe move this 06/22
		final CP_Path r = paths.outputRoot();
		final CompilationEnclosure ce = compilation.getCompilationEnclosure();
		final NextgenFactory nextgenFactory = ce.nextgenFactory();

		for (final EOT_OutputFile outputFile : l) {
			run_each(outputFile, r, paths, nextgenFactory);
		}

		paths.renderNodes();
	}

	private void run_each(final EOT_OutputFile outputFile,
						  final CP_Path r,
						  final CP_Paths paths,
						  final NextgenFactory nextgenFactory) {
		final String       path0 = outputFile.getFilename();
		final EG_Statement seq   = outputFile.getStatementSequence();

		final CP_Path      pp    = U.getPathForOutputFile(outputFile, r, path0);
		final ER_Node      node  = nextgenFactory.createERNode(pp, seq);
		paths.addNode(CP_RootType.OUTPUT, node);
	}

	private static void addLogs(final @NotNull List<EOT_OutputFile> l, final @NotNull IPipelineAccess aPa) {
		final CompilationEnclosure compilationEnclosure = aPa.getCompilationEnclosure();
		compilationEnclosure.__addLogs(l);
	}
}

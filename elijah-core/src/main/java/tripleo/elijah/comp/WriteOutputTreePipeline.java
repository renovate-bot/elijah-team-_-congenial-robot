package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.comp.nextgen.i.CP_RootType;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stages.logging.LogEntry;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

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
		CP_Path r = paths.outputRoot();

		for (final EOT_OutputFile outputFile : l) {
			final String       path0 = outputFile.getFilename();
			final EG_Statement seq   = outputFile.getStatementSequence();

			CP_Path pp;

			switch (outputFile.getType()) {
			case SOURCES -> pp = r.child("code2").child(path0);
			case LOGS -> pp = r.child("logs").child(path0);
			case INPUTS, BUFFERS -> pp = r.child(path0);
			case DUMP -> pp = r.child("dump").child(path0);
			case BUILD -> pp = r.child(path0);
			case SWW -> pp = r.child("sww").child(path0);
			default -> throw new IllegalStateException("Unexpected value: " + outputFile.getType());
			}

			//System.err.println("106 " + pp);

			paths.addNode(CP_RootType.OUTPUT, ER_Node.of(pp, seq));
		}

		paths.renderNodes();
	}

	private static void addLogs(final @NotNull List<EOT_OutputFile> l, final @NotNull IPipelineAccess aPa) {
		final List<ElLog> logs = aPa.getCompilationEnclosure().getPipelineLogic().elLogs;
		final String      s1   = logs.get(0).getFileName();

		for (final ElLog log : logs) {
			final List<EG_Statement> stmts = new ArrayList<>();

			if (log.getEntries().size() == 0) continue; // README Prelude.elijjah "fails" here

			for (final LogEntry entry : log.getEntries()) {
				final String logentry = String.format("[%s] [%tD %tT] %s %s", s1, entry.time, entry.time, entry.level, entry.message);
				stmts.add(new EG_SingleStatement(logentry + "\n"));
			}

			final EG_SequenceStatement seq      = new EG_SequenceStatement(new EG_Naming("wot.log.seq"), stmts);
			final String               fileName = log.getFileName().replace("/", "~~");
			final EOT_OutputFile       off      = new EOT_OutputFile(List_of(), fileName, EOT_OutputType.LOGS, seq);
			l.add(off);
		}
	}

}

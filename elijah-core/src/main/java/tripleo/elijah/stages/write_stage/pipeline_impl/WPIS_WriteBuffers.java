package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.WritePipeline;
import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.Old_GenerateResultItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class WPIS_WriteBuffers implements WP_Indiviual_Step {
	private final WritePipeline writePipeline;

	private final DeferredObject<LSPrintStream.LSResult, Object, Object> spsp               = new DeferredObject<LSPrintStream.LSResult, Object, Object>();
	private final DeferredObject<Compilation, Void, Void>                compilationPromise = new DeferredObject<>();

	@Contract(pure = true)
	public WPIS_WriteBuffers(final WritePipeline aWritePipeline) {
		writePipeline = aWritePipeline;

		compilationPromise.then(_m_bus::setCompilation);

		// TODO see if clj has anything for csp
		spsp.then((LSPrintStream.LSResult ls) -> {
			compilationPromise.then(c -> {
				final EOT_OutputFile outputFile = createOutputFile(ls, c, c.paths());
				_m_bus.addOutputFile(outputFile);
			});
		});
	}

	@NotNull
	private static EOT_OutputFile createOutputFile(final LSPrintStream.LSResult ls, final @NotNull Compilation c, final @NotNull CP_Paths paths) {
		final CP_Path or    = paths.outputRoot();
		final File    file1 = or.subFile("buffers.txt").toFile(); // TODO subFile vs child

		final List<EIT_Input> fs;
		final EG_Statement    sequence;

		if (ls == null) {
			sequence = new EG_SingleStatement("<<>>");
			fs       = List_of();
		} else {
			sequence = new EG_SequenceStatement(new EG_Naming("WriteBuffers"), ls.getStatement());
			fs       = ls.fs2(c);
		}

		//noinspection UnnecessaryLocalVariable
		final EOT_OutputFile off1 = new EOT_OutputFile(
				fs,
				file1.toString(),
				EOT_OutputType.BUFFERS,
				sequence);
		return off1;
	}

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final @NotNull WP_State_Control sc) {
		// 5. write buffers
		try {
			debug_buffers(st);
		} catch (FileNotFoundException aE) {
			sc.exception(aE);
		}
	}

	private void debug_buffers(final @NotNull WritePipelineSharedState st) throws FileNotFoundException {
		// TODO can/should this fail??

		compilationPromise.resolve(st.c);

		final List<Old_GenerateResultItem> generateResultItems1 = st.getGr().results();

		var or = st.c.paths().outputRoot();

		final CP_Path child = or.child("buffers.txt");

		child.getPathPromise().then((Path pp) -> {
			//final File   file = pp.toFile();
			//final String s1   = file.toString();
			//Stupidity.println_err_3("8383 " + s1);

			// TODO nested promises is a latch
			writePipeline.generateResultPromise.then((final @NotNull GenerateResult result) -> {
				final GenerateResult result1 = st.getGr();
				final LSPrintStream  sps     = new LSPrintStream();

				DebugBuffersLogic.debug_buffers_logic(result1, sps);

				final LSPrintStream.LSResult _s = sps.getResult();

				spsp.resolve(_s);
			});
		});
	}

	private final Bus _m_bus = new Bus();

	static class Bus {
		private Compilation c;

		public void setCompilation(final @NotNull Compilation cc) {
			c = cc;
		}

		public void addOutputFile(final @NotNull EOT_OutputFile off) {
			c.getOutputTree().add(off);
		}
	}
}

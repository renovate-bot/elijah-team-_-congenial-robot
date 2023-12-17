package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.inputtree.EIT_Input_HashSourceFile_Triple;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation;
import tripleo.util.buffer.DefaultBuffer;
import tripleo.util.buffer.TextBuffer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tripleo.elijah.util.Helpers.List_of;

public class WPIS_WriteInputs implements WP_Indiviual_Step {
	private final Map<String, Operation<String>> ops = new HashMap<>();

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final WP_State_Control sc) {
		var root = st.c.paths().outputRoot();
		var fn1  = root.child("inputs.txt");

		final DefaultBuffer buf = new DefaultBuffer("");

		final List<File> recordedreads = st.c.getIO().recordedreads;

		for (final File file : recordedreads) {
			final String fn = file.toString();

			final Operation<String> op = append_hash(buf, fn);

			ops.put(fn, op);

			if (op.mode() == Mode.FAILURE) {
				break;
			}
		}

		String s = buf.getText();

		final @NotNull EOT_OutputTree ot = st.c.getOutputTree();

		final List<EIT_Input_HashSourceFile_Triple> yys = new ArrayList<>();

		{
			for (final File file : recordedreads) {
				final String fn = file.toString();

				var decoded = EIT_Input_HashSourceFile_Triple.decode(fn);
				yys.add(decoded);
			}
		}

		final EG_SequenceStatement seq = new EG_SequenceStatement(
				new EG_Naming("<<WPIS_WriteInputs>>"),
				List_of(
						EG_Statement.of(s, () -> "<<WPIS_WriteInputs>> >> statement")
					   )
		);

		fn1.getPathPromise().then(pp -> {
			String string = "inputs.txt";//pp.toFile().toString(); //fn1.getPath().toFile().toString();

			final EOT_OutputFile off = new EOT_OutputFile(List_of(), new EOT_OutputFile.DefaultFileNameProvider(string), EOT_OutputType.INPUTS, seq);

			off.x = yys;

			ot.add(off);
		});
	}

	public @NotNull Operation<String> append_hash(@NotNull TextBuffer outputBuffer, @NotNull String aFilename) {
		final @NotNull Operation<String> hh2 = Helpers.getHashForFilename(aFilename);

		if (hh2.mode() == Mode.SUCCESS) {
			final String hh = hh2.success();

			assert hh != null;

			// TODO EG_Statement here

			outputBuffer.append(hh);
			outputBuffer.append(" ");
			outputBuffer.append_ln(aFilename);
		}

		return hh2;
	}
}

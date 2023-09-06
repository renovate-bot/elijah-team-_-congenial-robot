package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.Old_GenerateResultItem;

import java.text.MessageFormat;
import java.util.List;

public enum DebugBuffersLogic {
	;

	public static void __debug_buffers_logic_each(final @NotNull XPrintStream db_stream, final @NotNull Old_GenerateResultItem ab) {
		if (true) {
			final String s = MessageFormat.format("{0} - {1} - {2}", ab.counter, ab.ty, ab.output);

			if (db_stream instanceof LSPrintStream lsp) {
				lsp.addFile(ab.output);
			}

			db_stream.println("---------------------------------------------------------------");
			db_stream.println(s);
			db_stream.println(ab.node().identityString());
			db_stream.println(ab.buffer().getText());
			db_stream.println("---------------------------------------------------------------");
		}
	}

	public static void debug_buffers_logic(final @NotNull GenerateResult result, final @NotNull XPrintStream db_stream) {
		final List<Old_GenerateResultItem> generateResultItems = result.results();
		for (final Old_GenerateResultItem ab : generateResultItems) {
			__debug_buffers_logic_each(db_stream, ab);
		}
	}
}

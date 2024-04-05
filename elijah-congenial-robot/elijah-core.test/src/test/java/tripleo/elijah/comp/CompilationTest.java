package tripleo.elijah.comp;

import org.hamcrest.core.IsEqual;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Test;

import tripleo.elijah_durable_congenial.comp.IO;
import tripleo.elijah_durable_congenial.comp.StdErrSink;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.signal.DeducePipeline_finishedSignal;
import tripleo.elijah_durable_congenial.factory.comp.CompilationFactory;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.io.File;
import java.util.List;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
public class CompilationTest {

	@Test
	public final void testEz() throws Exception {
		final List<String> args = List_of("test/comp_test/main3", "-sE"/*, "-out"*/);
		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);

		assertEquals(true, c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE));

		final String pathname = "test/comp_test/main3/main3.ez";
		final String pathname1 = "test/comp_test/main3/main3.elijah";
		final String pathname2 = "test/comp_test/fact1.elijah";

		assertTrue(c.getIO().recordedRead(new File(pathname)));
		assertTrue(c.getIO().recordedRead(new File(pathname1)));
		assertTrue(c.getIO().recordedRead(new File(pathname2)));

		var REPORTS = c.reports();

		assertThat(REPORTS.codeInputSize(),  equalTo(1000));
		assertThat(REPORTS.codeOutputSize(), equalTo(1000));

		// ListAssertions.assertThat(REPORTS.codeOutputs()).containsExactlyInAnyOrder(pathname, pathname1, pathname2);

		//assertTrue(REPORTS.containsCodeInput(pathname));

		assertTrue(REPORTS.containsCodeInput(pathname1));
		assertTrue(REPORTS.containsCodeInput(pathname2));

		final int[] module_count_from_compilation = {0};
		c.eachModule((mod -> {
			SimplePrintLoggerToRemoveSoon.println_out_2(String.format("**48** %s %s", mod, mod.getFileName()));
			module_count_from_compilation[0]++;
		}));


		// README 11/12 module_count_from_compilation == c.modules().size()
		assertThat(module_count_from_compilation[0], equalTo(6));
		assertTrue(module_count_from_compilation[0] > 2);
	}

}

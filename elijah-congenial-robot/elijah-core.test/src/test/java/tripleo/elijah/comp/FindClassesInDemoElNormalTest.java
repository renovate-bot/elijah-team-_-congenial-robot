package tripleo.elijah.comp;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah_durable_congenial.comp.IO;
import tripleo.elijah_durable_congenial.comp.StdErrSink;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.signal.DeducePipeline_finishedSignal;
import tripleo.elijah_durable_congenial.entrypoints.MainClassEntryPoint;
import tripleo.elijah_durable_congenial.factory.comp.CompilationFactory;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.util.Helpers;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Tripleo
 */
public class FindClassesInDemoElNormalTest {

	@Ignore
	@Test
	public final void testParseFile() throws Exception {
		final List<String> args = Helpers.List_of("test/demo-el-normal", "test/demo-el-normal/main2", "-sE");
		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);
		assertEquals(true, c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE));

		final List<ClassStatement> aClassList = c.findClass("Main");
		for (final ClassStatement classStatement : aClassList) {
			SimplePrintLoggerToRemoveSoon.println_out_2(classStatement.getPackageName().getName());
		}
		Assert.assertEquals(1, aClassList.size());  // NOTE this may change. be aware
	}


	@Ignore
	@Test
	public final void testListFolders() throws Exception {
		final List<String> args = Helpers.List_of("test/demo-el-normal/listfolders/", "-sE");
		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);
		assertEquals(true, c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE));

		// searches all modules for top-level Main's that are classes (only the first from each module though)
		final List<ClassStatement> aClassList = c.findClass("Main");
		Assert.assertEquals(1, aClassList.size());

		Assert.assertFalse("isMainClass", MainClassEntryPoint.isMainClass(aClassList.get(0)));
	}

}

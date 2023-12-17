/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.junit.Assert;
import org.junit.Test;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

/**
 * @author Tripleo
 */
public class FindClassesInDemoElNormalTest {

	@Test
	public final void testParseFile() throws Exception {
		final List<String> args = tripleo.elijah.util.Helpers.List_of("test/demo-el-normal", "test/demo-el-normal/main2", "-sE");
		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);

		final List<ClassStatement> aClassList = c.findClass("Main");
		for (final ClassStatement classStatement : aClassList) {
			SimplePrintLoggerToRemoveSoon.println_out_2(classStatement.getPackageName().getName());
		}
		Assert.assertEquals(1, aClassList.size());  // NOTE this may change. be aware
	}


	@Test
	public final void testListFolders() throws Exception {
		final List<String> args = Helpers.List_of("test/demo-el-normal/listfolders/", "-sE");
		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);

		// searches all modules for top-level Main's that are classes (only the first from each module though)
		final List<ClassStatement> aClassList = c.findClass("Main");
		Assert.assertEquals(1, aClassList.size());

		Assert.assertFalse("isMainClass", MainClassEntryPoint.isMainClass(aClassList.get(0)));
	}

}

//
//
//

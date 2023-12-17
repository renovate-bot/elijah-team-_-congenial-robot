/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.hamcrest.core.IsEqual;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Test;

import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.io.File;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
public class CompilationTest {

	@Test
	public final void testEz() throws Exception {
		final List<String> args = List_of("test/comp_test/main3", "-sE"/*, "-out"*/);
		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);

		final String pathname = "test/comp_test/main3/main3.ez";
		final String pathname1 = "test/comp_test/main3/main3.elijah";
		final String pathname2 = "test/comp_test/fact1.elijah";

		assertTrue(c.getIO().recordedRead(new File(pathname)));
		assertTrue(c.getIO().recordedRead(new File(pathname1)));
		assertTrue(c.getIO().recordedRead(new File(pathname2)));

		// ListAssertions.assertThat(c.reports().codeOutputs()).containsExactlyInAnyOrder(pathname, pathname1, pathname2);

		//assertTrue(c.reports().containsCodeInput(pathname));
		assertTrue(c.reports().containsCodeInput(pathname1));
		assertTrue(c.reports().containsCodeInput(pathname2));

		final int[] module_count_from_compilation = {0};
		c.eachModule((mod -> {
			SimplePrintLoggerToRemoveSoon.println_out_2(String.format("**48** %s %s", mod, mod.getFileName()));
			module_count_from_compilation[0]++;
		}));


		// README 11/12 module_count_from_compilation == c.modules().size()
		Assert.<Integer>assertThat(module_count_from_compilation[0], new IsEqual<Integer>(6));
		assertTrue(module_count_from_compilation[0] > 2);
	}

}

//
//
//

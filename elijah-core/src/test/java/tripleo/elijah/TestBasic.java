/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_c.Emit;
import tripleo.elijah.util.Helpers;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
public class TestBasic {

	@Ignore
	@Test
	public final void testBasicParse() throws Exception {
		final List<String> ez_files = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final List<String> args     = new ArrayList<String>();
		args.addAll(ez_files);
		args.add("-sE");
		final ErrSink     eee = new StdErrSink();
		final Compilation c   = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		Assert.assertEquals(0, c.errorCount());
	}

	@Ignore
	@Test
	@SuppressWarnings("JUnit3StyleTestMethodInJUnit4Class")
	public final void testBasic() throws Exception {
		final List<String>          ez_files   = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final Map<Integer, Integer> errorCount = new HashMap<Integer, Integer>();
		int                         index      = 0;

		for (String s : ez_files) {
//			List<String> args = List_of("test/basic", "-sO"/*, "-out"*/);
			final ErrSink     eee = new StdErrSink();
			final Compilation c   = new CompilationImpl(eee, new IO());

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
			errorCount.put(index, c.errorCount());
			index++;
		}

		// README this needs changing when running make
		Assert.assertEquals(7, (int) errorCount.get(0)); // TODO Error count obviously should be 0
		Assert.assertEquals(20, (int) errorCount.get(1)); // TODO Error count obviously should be 0
		Assert.assertEquals(9, (int) errorCount.get(2)); // TODO Error count obviously should be 0
	}

	//@Ignore
	@Test
	@SuppressWarnings("JUnit3StyleTestMethodInJUnit4Class")
	public final void testBasic_listfolders3() throws Exception {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

		Emit.emitting = false;

		c.feedInputs(
				List_of(s, "-sO").stream()
						.map(CompilerInput::new)
						.collect(Collectors.toList()),
				new DefaultCompilerController());

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		Assert.assertEquals(2, c.errorCount()); // TODO Error count obviously should be 0


		final List<Pair<ErrSink.Errors, Object>> list = c.getErrSink().list();

		int i = 0;

		for (Pair<ErrSink.Errors, Object> pair : list) {
			var l = pair.getLeft();
			var r = pair.getRight();

			System.out.print(Integer.valueOf(i) + " ");
			i++;

			if (l == ErrSink.Errors.DIAGNOSTIC) {
				((Diagnostic) r).report(System.out);
			} else {
				System.out.println(r);
			}
		}
	}

	@Test
	public final void testBasic_listfolders3__() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

		c.feedInputs(
				List_of(s, "-sE").stream() // -sD??
						.map(CompilerInput::new)
						.collect(Collectors.toList()),
				new DefaultCompilerController());

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		var qq = c.con().createQualident(List_of("std", "io"));

		Assert.assertTrue(c.isPackage(qq.toString()));
	}

	@Ignore
	@Test
	public final void testBasic_listfolders4() throws Exception {
		String s = "test/basic/listfolders4/listfolders4.ez";

		final ErrSink     eee = new StdErrSink();
		final Compilation c   = new CompilationImpl(eee, new IO());

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		Assert.assertEquals(4, c.errorCount()); // TODO Error count obviously should be 0
	}

	@Test
	public final void testBasic_fact1() throws Exception {
		String s = "test/basic/fact1/main2";

		final ErrSink     eee = new StdErrSink();
		final Compilation c   = new CompilationImpl(eee, new IO());

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		Assert.assertEquals(25, c.errorCount()); // TODO Error count obviously should be 0
		Assert.assertTrue(c.getOutputTree().getList().size() > 0);
		Assert.assertTrue(c.getIO().recordedwrites.size() > 0);
	}

	@Test
	public final void testBasic_fact1_002() throws Exception {

		testBasic_fact1 f = new testBasic_fact1();
		f.start();

		Assert.assertEquals(25, f.c.errorCount()); // TODO Error count obviously should be 0

		var cot = f.c.getOutputTree();


		Multimap<String, EG_Statement> mms = ArrayListMultimap.create();


		for (EOT_OutputFile outputFile : cot.getList()) {
			if (outputFile.getType() != EOT_OutputType.SOURCES) continue;

			final String filename = outputFile.getFilename();
			System.err.println(filename);

			var ss = outputFile.getStatementSequence();

			mms.put(filename, ss);
/*
			if (ss instanceof EG_SequenceStatement seq) {
				for (EG_Statement statement : seq._list()) {
					var exp = statement.getExplanation();

					String txt = statement.getText();
				}
			}

			System.err.println(ss);
*/
		}

		List<Pair<String, String>> sspl = new ArrayList<>();

		for (Map.Entry<String, Collection<EG_Statement>> entry : mms.asMap().entrySet()) {
			var fn = entry.getKey();
			var ss = Helpers.String_join("\n", (entry.getValue()).stream().map(st -> st.getText()).collect(Collectors.toList()));

			//System.out.println("216 "+fn+" "+ss);

			sspl.add(Pair.of(fn, ss));
		}

		System.err.println(sspl);

		//System.err.println("nothing");
	}

	class testBasic_fact1 {

		Compilation c;

		public void start() throws Exception {
			String s = "test/basic/fact1/main2";

			c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}
	}
}

//
//
//

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
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_c.Emit;
import tripleo.elijah.util.Helpers;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
@SuppressWarnings("MagicNumber")
public class TestBasic {

	@Ignore @Test public final void testBasicParse() throws Exception {
		final List<String> ez_files = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final List<String> args     = new ArrayList<String>();
		args.addAll(ez_files);
		args.add("-sE");
		final Compilation c = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(args);

		assertEquals(0, c.errorCount());
	}

	@Ignore @Test public final void testBasic() throws Exception {
		final List<String>          ez_files   = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final Map<Integer, Integer> errorCount = new HashMap<Integer, Integer>();
		int                         index      = 0;

		for (String s : ez_files) {
//			List<String> args = List_of("test/basic", "-sO"/*, "-out"*/);
			final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
			errorCount.put(index, c.errorCount());
			index++;
		}

		// README this needs changing when running make
		assertEquals(7, (int) errorCount.get(0)); // TODO Error count obviously should be 0
		assertEquals(20, (int) errorCount.get(1)); // TODO Error count obviously should be 0
		assertEquals(9, (int) errorCount.get(2)); // TODO Error count obviously should be 0
	}

	//@Ignore
	@Test
	public final void testBasic_listfolders3() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation c = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		Emit.emitting = false;

		c.feedInputs(
				List_of(s, "-sO").stream()
						.map(CompilerInput::new)
						.collect(Collectors.toList()),
				new DefaultCompilerController());

		if (c.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}

		assertEquals(2, c.errorCount()); // TODO Error count obviously should be 0


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

		assertEquals(6, c.reports().codeOutputSize());

		assertTrue(c.reports().containsCodeOutput("/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.h"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/Main.h"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.c"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/Main.c"));
		assertTrue(c.reports().containsCodeOutput("/Prelude/Prelude.c"));
		assertTrue(c.reports().containsCodeOutput("/Prelude/Prelude.h"));
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

		assertTrue(c.isPackage(qq.toString()));

		//

		assertEquals(6, c.reports().codeInputSize());
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.collections/collections.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.math/math.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.io/Directory.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/Prelude.elijjah"));
		assertTrue(c.reports().containsCodeInput("test/basic/import_demo.elijjah"));
		assertTrue(c.reports().containsCodeInput("test/basic/listfolders3/listfolders3.elijah"));

		//

		assertEquals(6, c.reports().codeOutputSize());
		assertTrue(c.reports().containsCodeOutput("/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.h"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/Main.h"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.c"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/Main.c"));
		assertTrue(c.reports().containsCodeOutput("/Prelude/Prelude.c"));
		assertTrue(c.reports().containsCodeOutput("/Prelude/Prelude.h"));
	}

	@Ignore
	@Test
	public final void testBasic_listfolders4() throws Exception {
		String s = "test/basic/listfolders4/listfolders4.ez";

		final Compilation  c    = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		assertEquals(4, c.errorCount()); // TODO Error count obviously should be 0

		assertEquals(6, c.reports().codeInputSize());
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.collections/collections.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.math/math.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.io/Directory.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/Prelude.elijjah"));
		assertTrue(c.reports().containsCodeInput("test/basic/import_demo.elijjah"));
		assertTrue(c.reports().containsCodeInput("test/basic/listfolders4/listfolders3.elijah"));

		//

		assertEquals(6, c.reports().codeOutputSize());
		assertTrue(c.reports().containsCodeOutput("/listfolders4/wpkotlin_c.demo.list_folders/MainLogic.h"));
		assertTrue(c.reports().containsCodeOutput("/listfolders4/Main.h"));
		assertTrue(c.reports().containsCodeOutput("/listfolders4/wpkotlin_c.demo.list_folders/MainLogic.c"));
		assertTrue(c.reports().containsCodeOutput("/listfolders4/Main.c"));
		assertTrue(c.reports().containsCodeOutput("/Prelude/Prelude.c"));
		assertTrue(c.reports().containsCodeOutput("/Prelude/Prelude.h"));

	}

	@Test
	public final void testBasic_fact1() throws Exception {
		final String s = "test/basic/fact1/main2";
		final Compilation c   = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

		c.reports().turnAllOutputOff();

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}

		assertEquals(29, c.errorCount()); // TODO Error count obviously should be 0

		final List<EOT_OutputFile> outputFileList = c.getOutputTree().getList();
		assertEquals(15, outputFileList.size());

		final List<EOT_OutputFile> outputFileList1 = outputFileList.stream()
				.filter(ofq -> ofq.getType() == EOT_OutputType.SOURCES)
				.toList();

		assertEquals(4, outputFileList1.size());

		if (false) {
			outputFileList.stream()
					.map(outputFile -> "187 " + outputFile.toString())
					.forEach(System.err::println);
		}

		if (false) {
			outputFileList1.stream()
					.map(outputFile -> "182 " + outputFile.toString())
					.forEach(System.err::println);
		}

		final List<File> recordedwrites = c.getIO().recordedwrites;

		if (false) {
			recordedwrites.stream()
					.map(recordedwrite -> "194 " + recordedwrite.toString())
					.forEach(System.err::println);
		}

		assertEquals(15, recordedwrites.size());

		// TODO 11/04 Don't know if this is complete
		final Finally REPORTS = c.reports();

		assertEquals(7, REPORTS.codeInputSize());

		assertTrue(c.reports().containsCodeInput("test/basic/fact1/main2"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.collections/collections.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.math/math.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/std.io/Directory.elijjah"));
		assertTrue(c.reports().containsCodeInput("lib_elijjah/lib-c/Prelude.elijjah"));
		assertTrue(c.reports().containsCodeInput("test/basic/fact1/fact1.elijah"));
		assertTrue(c.reports().containsCodeInput("test/basic/fact1/main2/main2.elijah"));

		assertEquals(4, REPORTS.codeOutputSize());

		assertTrue(REPORTS.containsCodeOutput("/main2/Main.h"));
		assertTrue(REPORTS.containsCodeOutput("/Prelude/Prelude.c"));
		assertTrue(REPORTS.containsCodeOutput("/Prelude/Prelude.h"));
		assertTrue(REPORTS.containsCodeOutput("/main2/Main.c"));
	}

	@Test
	public final void testBasic_fact1_002() throws Exception {
		testBasic_fact1 f = new testBasic_fact1();
		f.start();

		// TODO 11/05 find out what these errors are...
		assertEquals(29, f.c.errorCount()); // TODO Error count obviously should be 0

		var cot = f.c.getOutputTree();

		Multimap<String, EG_Statement> mms = ArrayListMultimap.create();

		if (false) {
			for (EOT_OutputFile outputFile : cot.getList()) {
				if (outputFile.getType() != EOT_OutputType.SOURCES) continue;

				final String filename = outputFile.getFilename();
				if (false) {
					System.err.println("7777-289 "+filename);
				}

				var ss = outputFile.getStatementSequence();

				mms.put(filename, ss);

				if (false) {
					if (ss instanceof EG_SequenceStatement seq) {
						for (EG_Statement statement : seq._list()) {
							var exp = statement.getExplanation();

							String txt = statement.getText();
						}
					}

					System.err.println(ss);
				}
			}
		}

		if (false) {
			List<Pair<String, String>> sspl = new ArrayList<>();

			for (Map.Entry<String, Collection<EG_Statement>> entry : mms.asMap().entrySet()) {
				var fn = entry.getKey();
				var ss = Helpers.String_join("\n", (entry.getValue()).stream().map(st -> st.getText()).collect(Collectors.toList()));

				System.out.println("7777-216 "+fn+" "+ss);

				sspl.add(Pair.of(fn, ss));
			}
		}

		if (false) {
			//System.err.println("7777-271 " + sspl);

			//System.err.println("nothing");
		}


		// TODO 11/04 Don't know if this is complete
		final Finally REPORTS = f.c.reports();

		assertEquals(7, REPORTS.codeInputSize());
		assertTrue(REPORTS.containsCodeInput("lib_elijjah/lib-c/std.collections/collections.elijjah"));
		assertTrue(REPORTS.containsCodeInput("lib_elijjah/lib-c/std.math/math.elijjah"));
		assertTrue(REPORTS.containsCodeInput("lib_elijjah/lib-c/std.io/Directory.elijjah"));
		assertTrue(REPORTS.containsCodeInput("lib_elijjah/lib-c/Prelude.elijjah"));
		assertTrue(REPORTS.containsCodeInput("test/basic/fact1/fact1.elijah"));
		assertTrue(REPORTS.containsCodeInput("test/basic/fact1/main2/main2.elijah"));

		// FIXME 11/05 what is this? (the initial?)
		assertTrue(REPORTS.containsCodeInput("test/basic/fact1/main2"));

		// FIXME 11/05 .ezs are not inputs...

		//

		assertEquals(4, REPORTS.codeOutputSize());
		assertTrue(REPORTS.containsCodeOutput("/main2/Main.h"));
		assertTrue(REPORTS.containsCodeOutput("/Prelude/Prelude.c"));
		assertTrue(REPORTS.containsCodeOutput("/Prelude/Prelude.h"));
		assertTrue(REPORTS.containsCodeOutput("/main2/Main.c"));

	}

	class testBasic_fact1 {

		Compilation c;

		public void start() throws Exception {
			String s = "test/basic/fact1/main2";

			c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

			c.reports().turnAllOutputOff();

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0) {
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
			}
		}
	}
}

//
//
//

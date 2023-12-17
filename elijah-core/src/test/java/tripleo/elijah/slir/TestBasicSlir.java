/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.slir;

import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.lang.impl.OS_PackageImpl;
import tripleo.elijah.lang.imports.NormalImportStatement;
import tripleo.elijah.util.Helpers;

import java.io.IOException;

import static org.mockito.Mockito.mock;


/**
 * Created 11/6/21 8:11 AM
 */
public class TestBasicSlir {

//	//	@Test
//	public final void testBasic() throws IOException {
//		final List<String> ez_files = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
//		final Map<Integer, Integer> errorCount = new HashMap<Integer, Integer>();
//		int index = 0;
//
//		for (String s : ez_files) {
////			List<String> args = List_of("test/basic", "-sO"/*, "-out"*/);
//			final ErrSink eee = new StdErrSink();
//			final Compilation c = new Compilation(eee, new IO());
//
//			c.feedCmdLine(List_of(s, "-sO"));
//
//			if (c.errorCount() != 0)
//				System.err.println(String.format("Error count should be 0 but is %d for %s", c.errorCount(), s));
//			errorCount.put(index, c.errorCount());
//			index++;
//		}
//
//		// README this needs changing when running make
//		Assert.assertEquals(7, (int) errorCount.get(0)); // TODO Error count obviously should be 0
//		Assert.assertEquals(20, (int) errorCount.get(1)); // TODO Error count obviously should be 0
//		Assert.assertEquals(9, (int) errorCount.get(2)); // TODO Error count obviously should be 0
//	}
//
//	//		@Test
//	public final void testBasic_listfolders3() throws IOException {
//		String s = "test/basic/listfolders3/listfolders3.ez";
//
//		final ErrSink eee = new StdErrSink();
//		final Compilation c = new Compilation(eee, new IO());
//
//		c.feedCmdLine(List_of(s, "-sO"));
//
//		if (c.errorCount() != 0)
//			System.err.println(String.format("Error count should be 0 but is %d for %s", c.errorCount(), s));
//
//		Assert.assertEquals(5, c.errorCount()); // TODO Error count obviously should be 0
//	}
//
//	//		@Test
//	public final void testBasic_listfolders4() throws IOException {
//		String s = "test/basic/listfolders4/listfolders4.ez";
//
//		final ErrSink eee = new StdErrSink();
//		final Compilation c = new Compilation(eee, new IO());
//
//		c.feedCmdLine(List_of(s, "-sO"));
//
//		if (c.errorCount() != 0)
//			System.err.println(String.format("Error count should be 0 but is %d for %s", c.errorCount(), s));
//
//		Assert.assertEquals(5, c.errorCount()); // TODO Error count obviously should be 0
//	}

	@Ignore
	@Test
	public final void testBasic_fact1() throws IOException {
		String s0 = "test/basic/fact1";
		String s  = "test/basic/fact1/main2";

//		final Compilation c = new Compilation(new StdErrSink(), new IO());
//
//		c.feedCmdLine(List_of(s, "-sO"));

		final RootSlirNode   rsn = new RootSlirNode(mock(Compilation.class));
		final SlirSourceFile sf3 = new SlirSourceFile("lib_elijjah/lib-c/std.collections/collections.elijjah");
		final SlirSourceFile sf2 = new SlirSourceFile("Prelude.elijah");
		final SlirSourceFile sf0 = new SlirSourceFile(s0 + "/fact1.elijah");
		final SlirSourceFile sf1 = new SlirSourceFile(s + "/main2.elijah");

		{
			final SlirSourceNode sn1 = rsn.newSourceNode(sf1);

			final OS_Module mod = new OS_ModuleImpl();
			mod.setFileName(sf1.getFilename());

//			mod.setParent(compilation);
//			compilation.addModule(mod, fn);

//			mod.add/addIndexingStatement/addDocString

			final NormalImportStatement importStatement = new NormalImportStatement(mod);
			importStatement.addNormalPart(Helpers.string_to_qualident("wprust.demo.fact"));
			final SlirImportNode    sin1                  = sn1.addImport(importStatement);
			final SlirNamespaceNode fact_module_namespace = new SlirNamespaceNode(sf0, null, null);
			fact_module_namespace.markUsed("factorial", SlirPos.ALIAS);
			sin1.markImported(fact_module_namespace);

			final SlirClass sc1 = sn1.addClass("Main", null);
			sc1.annotate(SlirAnnotations.MAIN);
			final SlirFunctionNode main_function = sc1.addFunction("main", null);
			main_function.annotate(SlirAnnotations.MAIN);
			sc1.markUsed(main_function);

			sn1.useFunction(main_function);

			final SlirClass arguments = new SlirClass(sf2, "Arguments", null);
			sc1.setSuperClass(arguments);
			final SlirFunctionNode argument_function = sc1.addFunction("argument", null);
			arguments.markUsed(argument_function);
			sc1.markUsed(arguments);

			sn1.useClass(arguments);
			sn1.useFunction(argument_function);

			final SlirNamespaceNode prelude_namespace_import = new SlirNamespaceNode(sf2, "Prelude", null);

			final SlirClass system_integer = new SlirClass(prelude_namespace_import, "SystemInteger", null);
			final SlirClass prelude_string = new SlirClass(prelude_namespace_import, "String", null);
			prelude_string.markUsed("isInt", SlirPos.FUNCTION);
			prelude_string.markUsed("to_int", SlirPos.FUNCTION);
			sc1.markUsed(system_integer);
			sc1.markUsed(prelude_string);

			sn1.useClass(system_integer);
			sn1.useClass(prelude_string);

			final SlirFunctionNode prelude_println = new SlirFunctionNode(prelude_namespace_import, "println", null);
			sn1.useFunction(prelude_println);
		}

		{
			final SlirSourceNode sn2 = rsn.newSourceNode(sf0); // fact1.elijah

			final OS_Module mod = new OS_ModuleImpl();
			mod.setFileName(sf0.getFilename());

			final OS_Package      packageStatement = new OS_PackageImpl(Helpers.string_to_qualident("wprust.demo.fact"), 2);
			final SlirPackageNode sp1              = sn2.addPackage(packageStatement);

			// sp1/Main (class) is not live!

			final SlirNamespaceNode fact_module_namespace = new SlirNamespaceNode(sp1, null, null);
			fact_module_namespace.markUsed("factorial", SlirPos.ALIAS); // TODO verify by [main1]Main.main
			fact_module_namespace.markUsed("factorial_r", SlirPos.FUNCTION); // TODO mark by alias
//			sp1.markUsed(fact_module_namespace); // TODO what do we do here?

		}

		{
			final SlirSourceNode sn3 = rsn.newSourceNode(sf2); // Prelude

			final OS_Module mod = new OS_ModuleImpl();
			mod.setFileName(sf2.getFilename());

			final OS_Package      packageStatement = new OS_PackageImpl(Helpers.string_to_qualident("Prelude"), 1);
			final SlirPackageNode sp2              = sn3.addPackage(packageStatement);

			final SlirClass prelude_arguments      = new SlirClass(sp2, "Arguments", null);
			final SlirClass prelude_string         = new SlirClass(sp2, "Arguments", null);
			final SlirAlias prelude_system_integer = new SlirAlias(sp2, "Arguments", null);
			final SlirClass prelude_integer64      = new SlirClass(sp2, "Arguments", null);

			// TODO what to do now? check usages?
		}

		{
			final SlirSourceNode sn4 = rsn.newSourceNode(sf3); // collections

			final OS_Module mod = new OS_ModuleImpl();
			mod.setFileName(sf3.getFilename());

			final OS_Package      packageStatement = new OS_PackageImpl(Helpers.string_to_qualident("std.collections"), 4);
			final SlirPackageNode sp3              = sn4.addPackage(packageStatement);

			final SlirClass collections_list = new SlirClass(sp3, "List", null);
//			collections_list.specialize(List_of(prelude_string));

			// TODO I guess we check usages again
		}

		// TODO finish function
		// TODO refactor to sources
		// TODO add asserts
	}

}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

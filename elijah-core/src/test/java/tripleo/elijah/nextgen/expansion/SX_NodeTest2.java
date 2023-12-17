package tripleo.elijah.nextgen.expansion;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.util.Helpers;
import tripleo.small.ES_Symbol;

import java.util.List;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;

public class SX_NodeTest2 extends TestCase {

	@Ignore
	@Test
	public void testFullText() throws Exception {
		final Compilation comp = CompilationFactory.mkCompilationSilent(new StdErrSink(), new IO());

		final String f = "test/basic2/while100/";


		@NotNull final List<CompilerInput> inps = List_of(new CompilerInput(f));
		comp.feedInputs(inps, new DefaultCompilerController());

		//comp.feedCmdLine(List_of(f));

		final @NotNull EOT_OutputTree rt = comp.getOutputTree();

/*
		final SM_ClassDeclaration node = new SM_ClassDeclaration() {
			@Override
			public @Nullable SM_ClassBody classBody() {
				return null;
			}

			@Override
			public @NotNull SM_ClassInheritance inheritance() {
				return new SM_ClassInheritance() {
					@Override
					public @NotNull List<SM_Name> names() {
						return List_of(new SM_Name() {
							@Override
							public @NotNull String getText() {
								return "Arguments";
							}
						});
					}
				};
			}

			@Override
			public @NotNull SM_Name name() {
				return new SM_Name() {
					@Override
					public @NotNull String getText() {
						return "Main";
					}
				};
			}

			@Override
			public @NotNull SM_ClassSubtype subType() {
				return SM_ClassSubtype.NORMAL;
			}
		};
*/

//		fgen.forNode(node);

		final EG_SequenceStatement enc1 = getTestStatement();

//		final int yy = 2;
		System.out.println(enc1.getText());
//		System.out.println();
//		System.out.println(y);
		System.out.println();

		final List<EOT_OutputFile> l = rt.getList();
		assert l != null;

		final int yyy = 2;
		final List<EOT_OutputFile> wmainl = l.stream()
				.filter(eof -> eof.getFilename().replace("//", "/").equals("/while100/Main.c"))
				.collect(Collectors.toList());

		assert wmainl.size() > 0;

		wmainl.stream()
				.filter(wmain -> wmain.getType() == EOT_OutputType.SOURCES)
				.forEach(wmain -> {
					System.out.println("****************************");
					final EG_Statement seqs = wmain.getStatementSequence();
					System.out.println(seqs.getText());
				});
	}

	@NotNull
	private static EG_SequenceStatement getTestStatement() {
		// (syn include local "main.h" :rule c-interface-default)
		final EG_SyntheticStatement emh = new EG_SyntheticStatement(new EG_Naming("include", "local"), "main.h", new EX_Rule("c-interface-default"));
		// (syn include system (?) :Prelude :rule c-interface-prelude-default)
		final EG_SyntheticStatement eph = new EG_SyntheticStatement(new EG_Naming("include", "system"), new ES_Symbol("Prelude"), new EX_Rule("c-interface-prelude-default"));
		// (syn linebreak :rule c-break-after-includes)
		final EG_SyntheticStatement lb1 = new EG_SyntheticStatement(new EG_Naming("linebreak"), new ES_Symbol("linebreak"), new EX_Rule("c-interface-prelude-default"));
		lb1.setText("\n");
		// 8 -> (comp seq c-fn-hdr '("void" :tag fn Main::main/<rt-type>)
		//    '("z100main" :tag <class Main> :rule c-fn-code-main))
		final EG_SingleStatement a = new EG_SingleStatement("void").tag("fn Main::main/<rt-type>", 0);
		final EG_SingleStatement b = new EG_SingleStatement("z100main").tag("<class Main>", 0).rule("c-fn-code-main", 0);

		final EG_SingleStatement   mc91e1 = new EG_SingleStatement("Z100*").tag("<class Main>", 0).rule("c-class-code-main", 0);
		final EG_SingleStatement   mc91e2 = new EG_SingleStatement("C").tag("<class Main>", 0).rule("c-arg-c-for-current", 0);
		final EG_SequenceStatement mc91e  = new EG_SequenceStatement(new EG_Naming("xxx_fn-arg-1"), List_of(mc91e1, mc91e2));
		final EG_SequenceStatement mc91ee = new EG_SequenceStatement(new EG_Naming("xxx_arg-list"), "(", ")", List_of(mc91e));
		final EG_SequenceStatement mc8    = new EG_SequenceStatement(new EG_Naming("c-fn-hdr"), List_of(a, b, mc91ee));

		// 9+ -> (comp seq '({9.1} {9.2}))
		// 9.1 -> (sing c-simple-decl-assign
		//   ("int" :tag [i/c-type] :rule el-std-c-types)
		//   ("vvi" :tag [i] :rule el-std-var-name)
		//   ("=" :rule c-assignment)
		//   ("0" :rule el-c-inot-iterate-with-initial-increment)
		//   '(list '( ("Z100*" :rule c-class-code-main :rule c-class-ptr :rule c-class-z-prefix :tag <class Main>)
		//             ("C" :rule c-arg-c-for-current)))
		//   (";" :rule c-close-statement) // auto??

		final EG_SingleStatement mc91a = new EG_SingleStatement("int").tag("i/c-type", 0).rule("el-std-c-types", 0);
		final EG_SingleStatement mc91b = new EG_SingleStatement("vvi").tag("i", 0).rule("el-std-var-name", 0);
		final EG_SingleStatement mc91c = new EG_SingleStatement(" = ").rule("c-assignment", 0);
		final EG_SingleStatement mc91d = new EG_SingleStatement("0").tag("<class Main>", 0).rule("el-c-inot-iterate-with-initial-increment", 0);
		final EG_SingleStatement mc91f = new EG_SingleStatement(";").rule("c-close-statement", 0);

		final EG_SequenceStatement mc91 = new EG_SequenceStatement(new EG_Naming("xxx_int-decl"), List_of(mc91a, mc91b, mc91c, mc91d, mc91f));
		// 9.2.1 -> (comp seq while-loop-range-monotonic ("while") (LPAREN) (ENC??)
		//            ("vvi" :tag [with i])
		//            ("<=" :rule el-c-integer-iterator-pre-begin-test)
		//            ("100" :tag [to 100] :rule el-c-integer-iterator-to)
		//            (RPAREN))
		final EG_SingleStatement   cc    = new EG_SingleStatement("vvi").tag("with i", 0);
		final EG_SingleStatement   cd    = new EG_SingleStatement("<=").rule("el-c-integer-iterator-pre-begin-test", 0);
		final EG_SingleStatement   ce    = new EG_SingleStatement("100").tag("to 100", 0).rule("c-integer-iterator-to", 0);
		final EG_SequenceStatement mc921 = new EG_SequenceStatement("while(", ")", List_of(cc, cd, ce));
		// 9.2.2.1 -> (comp sing "vvi++" (postinc "vvi")?? :rule el-integer-iterate-monotonic
		//              :tag [with i])
		final EG_SingleStatement mc9221 = new EG_SingleStatement("vvi++;").tag("with i", 0).rule("el-integer-iterate-monotonic", 0); // seq no-space
		// 9.2.2.2 -> (comp sing fn-call (("println_int" [:ref Prelude/println @( Prelude/SystemInteger )]) // list vs list
		//                                ("vvi" :tag [with i]))
		final EG_SingleStatement   mc9222a = new EG_SingleStatement("println_int"); // TODO ref //.tag("with i", 0).rule("el-integer-iterate-monotonic", 0); // seq no-space
		final EG_SingleStatement   mc9222b = new EG_SingleStatement("(");
		final EG_SingleStatement   mc9222c = new EG_SingleStatement("vvi").tag("with i", 0); // seq no-space
		final EG_SingleStatement   mc9222d = new EG_SingleStatement(");");
		final EG_SequenceStatement mc9222  = new EG_SequenceStatement(new EG_Naming("xxx_fn-call-1"), List_of(mc9222a, mc9222b, mc9222c, mc9222d));
		// 9.2.2 -> (comp seq {9.2.2.1} {9.2.2.2})
		final EG_SequenceStatement mc922 = new EG_SequenceStatement("{", "}", List_of(mc9221, mc9222));
		// 9.2 -> (comp enc {9.2.1} BRACES {9.2.2})
		final EG_SequenceStatement mc92 = new EG_SequenceStatement(new EG_Naming("xxx1"), List_of(mc921, mc922));
		final EG_SequenceStatement mc9p = new EG_SequenceStatement(new EG_Naming("xxx_fn-def"), List_of(mc91, mc92));
		// 9++ -> (comp enc {9+} BRACES ...???)
		final EG_SequenceStatement mc9pp = new EG_SequenceStatement("{", "}", List_of(mc9p));
		// (comp enc {8} BRACES {9++})
		final EG_SequenceStatement enc1 = new EG_SequenceStatement(new EG_Naming("enc1"), List_of(mc8, mc9pp));

		final String y = Helpers.String_join("\n", List_of(emh, eph, lb1, enc1).stream()
				.map((EG_Statement x) -> x.getText())
				.collect(Collectors.toList()));
		return enc1;
	}
}

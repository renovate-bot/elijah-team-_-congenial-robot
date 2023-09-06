///*
// * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
// *
// * The contents of this library are released under the LGPL licence v3,
// * the GNU Lesser General Public License text was downloaded from
// * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
// *
// * # Eli Bendersky [http://eli.thegreenplace.net]
// * # This code is in the public domain.
// */
//package tripleo.elijah.typinf.parserT;
//
//import org.junit.Test;
//import tripleo.elijah.typinf.Decl_AST;
//import tripleo.elijah.typinf.parser.ParseError;
//import tripleo.elijah.typinf.parser.Parser;
//
//import static org.junit.Assert.assertEquals;
//
//public class TestParser {
//
//	public String str(Object a) {
//		return a.toString();
//	}
//
//	/**
//	 * Helper for testing parsed code.
//	 * <p>
//	 * Parses s into an AST node and asserts that its string representation
//	 * is equal to declstr.
//	 */
//	void assertParsed(String s, String declstr) throws ParseError {
//		Parser p = new Parser();
//
//		Decl_AST node = p.parse_decl(s);
//		assertEquals(declstr, str(node));
//	}
//
//	@Test
//	public void test_basic_decls() throws ParseError {
//		this.assertParsed("foo x = 2", "Decl(foo, Lambda([x], 2))");
//		this.assertParsed("foo x = false", "Decl(foo, Lambda([x], false))");
//		this.assertParsed("foo x = joe", "Decl(foo, Lambda([x], joe))");
//		this.assertParsed("foo x = (joe)", "Decl(foo, Lambda([x], joe))");
//	}
//
//	@Test
//	public void test_parse_multiple() throws ParseError {
//		Parser   p  = new Parser();
//		Decl_AST n1 = p.parse_decl("foo x = 10");
//		assertEquals("Decl(foo, Lambda([x], 10))", str(n1));
//		Decl_AST n2 = p.parse_decl("foo y = true");
//		assertEquals("Decl(foo, Lambda([y], true))", str(n2));
//	}
//
//
//	@Test
//	public void test_basic_ifexpr() throws ParseError {
//		this.assertParsed("foo x = if y then z else q",
//						  "Decl(foo, Lambda([x], If(y, z, q)))");
//	}
//
//	@Test
//	public void test_basic_op() throws ParseError {
//		this.assertParsed("bar z y = z + y",
//						  "Decl(bar, Lambda([z, y], (z + y)))");
//	}
//
//	@Test
//	public void test_basic_proc() throws ParseError {
//		this.assertParsed("bar z y = lambda f -> z + (y * f)",
//						  "Decl(bar, Lambda([z, y], Lambda([f], (z + (y * f)))))");
//	}
//
//	@Test
//	public void test_basic_app() throws ParseError {
//		this.assertParsed("foo x = gob(x)",
//						  "Decl(foo, Lambda([x], App(gob, [x])))");
//		this.assertParsed("foo x = bob(x, true)",
//						  "Decl(foo, Lambda([x], App(bob, [x, true])))");
//		this.assertParsed("foo x = bob(x, max(10), true)",
//						  "Decl(foo, Lambda([x], App(bob, [x, App(max, [10]), true])))");
//	}
//
//	@Test
//	public void test_full_exprs() throws ParseError {
//		this.assertParsed(
//				"bar = if ((t + p) * v) > 0 then x else f(y)",
//				"Decl(bar, If((((t + p) * v) > 0), x, App(f, [y])))");
//		this.assertParsed(
//				"bar = joe(moe(doe(false)))",
//				"Decl(bar, App(joe, [App(moe, [App(doe, [false])])]))");
//		this.assertParsed(
//				"cake = lambda f -> lambda x -> f(3) - f(x)",
//				"Decl(cake, Lambda([f], Lambda([x], (App(f, [3]) - App(f, [x])))))");
//		this.assertParsed(
//				"cake = lambda f x -> f(3) - f(x)",
//				"Decl(cake, Lambda([f, x], (App(f, [3]) - App(f, [x]))))");
//	}
//}
//
////
////
////

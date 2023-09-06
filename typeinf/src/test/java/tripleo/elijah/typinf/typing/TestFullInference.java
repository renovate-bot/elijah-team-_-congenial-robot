///*
// * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
// *
// * The contents of this library are released under the LGPL licence v3,
// * the GNU Lesser General Public License text was downloaded from
// * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
// *
// */
//package tripleo.elijah.typinf.typing;
//
//import org.jetbrains.annotations.NotNull;
//import org.junit.Test;
//import tripleo.elijah.typinf.*;
//import tripleo.elijah.typinf.parser.ParseError;
//import tripleo.elijah.typinf.parser.Parser;
//
//import java.util.HashMap;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static tripleo.elijah.typinf.TypInf.*;
//import static tripleo.elijah.util.Helpers.List_of;
//
///**
// * # Eli Bendersky [http://eli.thegreenplace.net]
// * # This code is in the public domain.
// * <p>
// * Created 9/4/21 4:51 AM
// */
//public class TestFullInference {
//	/**
//	 * Assert that the type of the declaration is inferred to ty.
//	 *
//	 * @param decl to parse
//	 * @param ty   expected string
//	 */
//	void assertInferredType(String decl, String ty) throws ParseError {
//		Parser   p = new Parser();
//		Decl_AST e = p.parse_decl(decl);
//
//		assign_typenames(e.expr);
//
//		List<TypInf.TypeEquation> equations = List_of();
//
//		generate_equations(e.expr, equations);
//
//		HashMap<String, Type> unifier = unify_all_equations(equations);
//
//		Type inferred = get_expression_type(e.expr, unifier, true);
//
//		assertEquals(ty, str(inferred));
//	}
//
//	public String str(@NotNull Object a) {
//		if (a instanceof IntType it) {
//			return a.toString();
//		}
//		if (a instanceof BoolType bt) {
//			return a.toString();
//		}
//		if (a instanceof TypInf.FuncType ft) {
//			return a.toString();
//		}
//		if (a instanceof TypInf.TypeVar tv) {
//			return tv.getName();
//		}
//		return a.toString();
//	}
//
//	@Test
//	public void test_simple() throws ParseError {
//		this.assertInferredType("foo = 9", "Int");
//		this.assertInferredType("foo = false", "Bool");
//		this.assertInferredType("foo x = 9", "(a0 -> Int)"); // was (a -> Int)
//		this.assertInferredType("foo x = true", "(a0 -> Bool)"); // was (a -> Bool)
//		this.assertInferredType("foo x y = x + y", "((Int, Int) -> Int)");
//		this.assertInferredType("foo x y = x == y", "((Int, Int) -> Bool)");
//	}
//
//	@Test
//	public void test_if() throws ParseError {
//		this.assertInferredType(
//				"foo x y = if x > y then 10 else 20",
//				"((Int, Int) -> Int)");
//		this.assertInferredType(
//				"foo x = if x then 10 else 20",
//				"(Bool -> Int)");
//		this.assertInferredType(
//				"foo x = if x then x else x",
//				"(Bool -> Bool)");
//	}
//
//	@Test
//	public void test_full() throws ParseError {
//		this.assertInferredType(
//				"foo f = f(11)",
//				"((Int -> a) -> a)");
//		this.assertInferredType(
//				"foo g h = g(h(0))",
//				"(((b -> a), (Int -> b)) -> a)");
//		this.assertInferredType(
//				"foo f g x = f(g(3 + x))",
//				"(((b -> a), (Int -> b), Int) -> a)");
//		this.assertInferredType(
//				"foo f x = f(3) - f(x)",
//				"(((Int -> Int), Int) -> Int)");
//		this.assertInferredType(
//				"foo f g x = if f(x) then g(x) else 20",
//				"(((a -> Bool), (a -> Int), a) -> Int)");
//		this.assertInferredType(
//				"foo f g x = if f(x == 1) then g(x) else 20",
//				"(((Bool -> Bool), (Int -> Int), Int) -> Int)");
//		this.assertInferredType(
//				"foo f = lambda t -> f(t)",
//				"((b -> a) -> (b -> a))");
//		this.assertInferredType(
//				"foo f x = if x then lambda t -> f(t) else lambda j -> f(x)",
//				"(((Bool -> a), Bool) -> (Bool -> a))");
//	}
//}
//
////
////
////

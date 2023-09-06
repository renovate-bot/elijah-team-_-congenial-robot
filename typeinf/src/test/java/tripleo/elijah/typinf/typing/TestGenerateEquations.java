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
//import org.junit.Before;
//import org.junit.Test;
//import tripleo.elijah.typinf.Decl_AST;
//import tripleo.elijah.typinf.TypInf;
//import tripleo.elijah.typinf.parser.ParseError;
//import tripleo.elijah.typinf.parser.Parser;
//
//import java.util.List;
//
//import static org.junit.Assert.assertTrue;
//import static tripleo.elijah.util.Helpers.List_of;
//
///**
// * # Eli Bendersky [http://eli.thegreenplace.net]
// * # This code is in the public domain.
// * <p>
// * Created 9/4/21 4:43 AM
// */
//public class TestGenerateEquations {
//
//	@Test
//	public void test_decl() throws ParseError {
//		Parser                             p         = new Parser();
//		Decl_AST                           e         = p.parse_decl("foo f x = f(3) - f(x)");
//		@NotNull List<TypInf.TypeEquation> equations = List_of();
//		TypInf.assign_typenames(e.expr);
//		TypInf.generate_equations(e.expr, equations);
//		assertTrue(has_equation(equations, "t1", "(Int -> t4)"));
//		assertTrue(has_equation(equations, "t1", "(t2 -> t5)"));
//	}
//
//	/**
//	 * Assert that the list of equations eqs has a left=right equation.
//	 * <p>
//	 * left and right are given in string representations.
//	 *
//	 * @param eqs
//	 * @return
//	 */
//	boolean has_equation(@NotNull List<TypInf.TypeEquation> eqs, String left, String right) {
//		for (TypInf.TypeEquation e : eqs) {
//			if (str(e.left()).equals(left) && str(e.right()).equals(right))
//				return true;
//		}
//		return false;
//	}
//
//	@Before
//	public void setUp() {
//		TypInf.reset_type_counter();
//	}
//
//	public String str(@NotNull Object a) {
//		return a.toString();
//	}
//
//}
//
////
////
////

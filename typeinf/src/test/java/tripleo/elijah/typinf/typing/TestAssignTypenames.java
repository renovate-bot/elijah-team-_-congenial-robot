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
//import org.junit.Test;
//import tripleo.elijah.typinf.*;
//import tripleo.elijah.typinf.parser.ParseError;
//import tripleo.elijah.typinf.parser.Parser;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * # Eli Bendersky [http://eli.thegreenplace.net]
// * # This code is in the public domain.
// * <p>
// * Created 9/3/21 9:54 PM
// */
//public class TestAssignTypenames {
//
//	public String str(Object a) {
//		return a.toString();
//	}
//
//	@Test
//	public void test_decl() throws ParseError {
//		Parser   p = new Parser();
//		Decl_AST e = p.parse_decl("foo f x = f(3) - f(x)");
//		TypInf.assign_typenames(e.expr);
//
//		final LambdaExpr_AST lambdaExpr = (LambdaExpr_AST) e.expr;
//		final OpExpr_AST     opExpr     = (OpExpr_AST) lambdaExpr.expr;
//
//		final AppExpr_AST leftExpr  = (AppExpr_AST) opExpr.left;
//		final AppExpr_AST rightExpr = (AppExpr_AST) opExpr.right;
//
//		assertEquals("t1", str(leftExpr.func.get_type()));
//		assertEquals("t1", str(rightExpr.func.get_type()));
//		assertEquals("t2", str(rightExpr.args.get(0).get_type()));
//
//		assertEquals("t3", str(opExpr.get_type()));
//		assertEquals("t4", str(leftExpr.get_type()));
//		assertEquals("t5", str(rightExpr.get_type()));
//	}
//}
//
////
////
////

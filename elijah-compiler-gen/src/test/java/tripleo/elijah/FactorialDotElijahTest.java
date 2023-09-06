/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah;

import org.junit.Assert;
import org.junit.Test;
import tripleo.elijah.comp.GenBuffer;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.ModuleRef;
import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.gen.nodes.*;
import tripleo.util.buffer.TextBuffer;

import static tripleo.elijah.util.Helpers.List_of;

public class FactorialDotElijahTest {

	@Test
	public void testGenMethName() {
//		final ModuleRef prelude = new ModuleRef(null, -1);
//		final TypeRef   u64     = new TypeRef(prelude, prelude, "u64", 81);
//		final ModuleRef main_m  = new ModuleRef("fact.elijah", -2);
//		final TypeRef   main_k  = new TypeRef(main_m, main_m, "Main", 100);
//
//
//		final ArgumentNode argumentNode = new ArgumentNode("i", u64);
//		Assert.assertEquals("vai", argumentNode.getGenName());
//
//		final MethHdrNode mhn=new MethHdrNode(u64, main_k, "factorial_r",
//				List_of(argumentNode), 1000);
//		Assert.assertEquals("z100factorial_r", mhn.genName());
	}

	@Test
	public void nMinusOne() {
//		final CompilerContext cctx = new CompilerContext("ll");
//		final ModuleRef prelude = new ModuleRef(null, -1);
//		final TypeRef   u64     = new TypeRef(prelude, prelude, "u64", 81);
//		final ModuleRef main_m  = new ModuleRef("fact.elijah", -2);
//		final TypeRef   main_k  = new TypeRef(main_m, main_m, "Main", 100);
//
//
//		final MethHdrNode mhn=new MethHdrNode(u64, main_k, "factorial_r",
//				List_of(new ArgumentNode("i", u64)), 1000);
//		final CaseHdrNode shn=new CaseHdrNode(ExpressionNodeBuilder.varref("i", mhn, u64));
//
//
//		final TmpSSACtxNode tccssan = new TmpSSACtxNode(cctx);
//		final LocalAgnTmpNode lamn=new LocalAgnTmpNode(tccssan, ExpressionNodeBuilder.binex(u64,
//				ExpressionNodeBuilder.varref("n", shn, u64),
//											ExpressionOperators.OP_MINUS,
//											ExpressionNodeBuilder.integer(1)));
////		BeginTmpSSACtx(cctx, tccssan, gbn);
//
//		final String s = tccssan.getType().genText(cctx);
//		Assert.assertEquals("vtn - 1", s);
//
//		final String s1 = (lamn.getLeft().genText(cctx));
//		Assert.assertEquals("vt1", s1); // TODO no idea if this is right
//
//		final String s2 = (lamn.getRight().genText(cctx));
//		Assert.assertEquals("vtn - 1", s2); // TODO no idea if this is right
//
	}

	@Test
	public void testTmpSSACtx() {
//		final CompilerContext cctx = new CompilerContext("ll");
//		final GenBuffer       gbn  = new GenBuffer();
//
//		final ModuleRef prelude = new ModuleRef(null, -1);
//		final TypeRef   u64     = new TypeRef(prelude, prelude, "u64", 81);
//		final ModuleRef main_m  = new ModuleRef("fact.elijah", -2);
//		final TypeRef   main_k  = new TypeRef(main_m, main_m, "Main", 100);
//
//		final MethRef   fact_r  = new MethRef("factorial_r", main_k, 1001);
//		fact_r.setReturnType(u64);
//		fact_r.setArgTypes(u64);
//
//		final MethHdrNode mhn=new MethHdrNode(u64, main_k, "factorial_r",
//				List_of(new ArgumentNode("i", u64)), 1001);
//		final CaseHdrNode shn=new CaseHdrNode(ExpressionNodeBuilder.varref("i", mhn, u64));
//
//		final TmpSSACtxNode tccssan = new TmpSSACtxNode(cctx);
//		final LocalAgnTmpNode lamn=new LocalAgnTmpNode(tccssan, ExpressionNodeBuilder.binex(u64,
//				ExpressionNodeBuilder.varref("n", shn, u64),
//				ExpressionOperators.OP_MINUS, ExpressionNodeBuilder.integer(1)));
//		final TextBuffer b1 = FactorialR.BeginTmpSSACtx(cctx, tccssan, gbn);
////		Assert.assertEquals("{\n\tu64 ", b1.getText()); // TODO maybe this wll be right in the future.
//		Assert.assertEquals("{\n\tZ81 ", b1.getText());
//
//		final TmpSSACtxNode tccssan2 = new TmpSSACtxNode(cctx);
//		final LocalAgnTmpNode latn2=new LocalAgnTmpNode(tccssan2, ExpressionNodeBuilder.fncall(
//				fact_r, List_of(lamn)));
//		final TextBuffer b2 = FactorialR.GenLocalAgn(cctx, latn2, gbn);
//		Assert.assertEquals("{\n" +
//				"\tZ81 vt2 = z100factorial_r(vt1);\n" +
//				"\t", b2.getText());
	}

}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.GenBuffer;
import tripleo.elijah.gen.nodes.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang2.BuiltInTypes;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.util.buffer.*;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(sb)
 *
 */
public class FactorialR /* extends TestCase */ {

	public FactorialR(/*String name*/) {
//		super(name);
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected void tearDown() throws Exception {
	}

	void main2_el(final CompilerContext cctx, final @NotNull GenBuffer gbn) {
		final ModuleRef prelude = new ModuleRef(null, -1);
		final TypeRef   sys_int = new TypeRef(prelude, prelude, "SystemInteger", BuiltInTypes.SystemInteger.getCode());
		final ModuleRef main2   = new ModuleRef("main2/main2.elijjah", 10000);
		final TypeRef   main    = new TypeRef(main2, main2, "Main", 100);
				
		gbn.InitMod(cctx, "main2/main2.elijjah");
		final ImportNode impn = new ImportNode("wprust.demo.fact");
		gbn.GenImportStmt(cctx, impn);
		
		final ClassDeclNode cdn = new ClassDeclNode("Main", null,
				List_of(new Inherited("Arguments", false))); // gen inh code 
		cdn.GenClassDecl(cctx, gbn);
		
		final MethHdrNode mhn = new MethHdrNode(null, cdn.type(), "main", null, 1000);
		mhn.GenMethHdr(cctx, gbn);
		mhn.BeginMeth(cctx, gbn);
		
		final LocalDeclAgnNode ldan1 = new LocalDeclAgnNode("b1", ExpressionNodeBuilder.integer(3));
		gbn.GenLocalDeclAgn(cctx, ldan1);
		
		final LocalDeclAgnNode ldan_a1 = new LocalDeclAgnNode("a1", sys_int,
				ExpressionNodeBuilder.fncall("argument", 
						convertToLocalAgnTmpNode(List_of(ExpressionNodeBuilder.integer(1)))));
		gbn.GenLocalDeclAgn(cctx, ldan_a1);
		
		final TmpSSACtxNode latn = new TmpSSACtxNode(cctx);
		latn.GenLocalAgn(cctx, gbn);
		
		final IfNode ifn = new IfNode(latn);
		ifn.BeginIfCtx(cctx, gbn);
		
		final LocalAgnNode lan_b1 = new LocalAgnNode(
				ExpressionNodeBuilder.fncall("a1.toInt", null));
		lan_b1.GenLocalAgn(cctx, gbn);
		ifn.CloseIfCtx(cctx, gbn);
		
		final LocalDeclAgnNode ldan_f1 = new LocalDeclAgnNode("f1",
				convertToLocalAgnTmpNode2(ExpressionNodeBuilder.fncall("factorial",  List_of(new LocalAgnTmpNode("b1")))));
		gbn.GenLocalAgn(cctx, ldan_f1);
		
		final SimpleFnCall sfc = new SimpleFnCall("print", List_of("f1"));
		sfc.GenFnCall(cctx, gbn);
		mhn.EndMeth(cctx, gbn);
		cdn.CloseClassDecl(cctx, gbn);
		
	}
	
	
	private @NotNull NumericExpression convertToLocalAgnTmpNode2(final IExpressionNode fncall) {
		throw new NotImplementedException();
	}

	private List<LocalAgnTmpNode> convertToLocalAgnTmpNode(final List<@NotNull NumericExpression> list_of) {
		// TODO Auto-generated method stub
		final List<LocalAgnTmpNode> r = null;
		throw new NotImplementedException();
//		return r;
	}

	public void factorial_r(final @NotNull CompilerContext cctx, final @NotNull GenBuffer gbn) {
		final ModuleRef prelude = new ModuleRef(null, -1);
		final TypeRef   u64     = new TypeRef(prelude, prelude, "u64", 81);
		final ModuleRef main_m  = new ModuleRef("fact.elijah", -2);
		final TypeRef   main_k  = new TypeRef(main_m, main_m, "Main", 100);


		final MethHdrNode mhn = new MethHdrNode(u64, main_k, "factorial_r",
												List_of(new ArgumentNode("i", u64)), 1000);
		GenMethHdr(cctx, mhn, gbn);
		BeginMeth(cctx, mhn, gbn);
		
		final CaseHdrNode shn=new CaseHdrNode(ExpressionNodeBuilder.varref("i", mhn, u64));
		BeginCaseStatement(cctx, shn, gbn);
		
		final CaseChoiceNode csn=new CaseChoiceNode(ExpressionNodeBuilder.integer(0), shn);
		BeginCaseChoice(cctx, csn, gbn);
		
//		ReturnAgnSimpleIntNode rasin=new ReturnAgnSimpleIntNode(ExpressionNodeBuilder.integer(1));
		final ReturnAgnNode rasin=new ReturnAgnNode(ExpressionNodeBuilder.integer(1));
//		GenReturnAgnSimpleInt(cctx, rasin, gbn);
		GenReturnAgn(cctx, rasin, gbn);
		
		final CloseCaseNode cccn1 = new CloseCaseNode(csn, ChoiceOptions.BREAK);
		CloseCaseChoice(cctx, cccn1, gbn);

		final CaseChoiceNode csn2 = new CaseChoiceNode(cctx, ExpressionNodeBuilder.varref("n", shn, u64), shn);
		BeginDefaultCaseStatement(cctx, csn2, gbn);
		
		final TmpSSACtxNode tccssan = new TmpSSACtxNode(cctx);
		final LocalAgnTmpNode lamn=new LocalAgnTmpNode(tccssan, ExpressionNodeBuilder.binex(u64,
				ExpressionNodeBuilder.varref("n", shn, u64),
				ExpressionOperators.OP_MINUS, ExpressionNodeBuilder.integer(1)));
		BeginTmpSSACtx(cctx, tccssan, gbn);
		
		final TmpSSACtxNode tccssan2 = new TmpSSACtxNode(cctx);
		final LocalAgnTmpNode latn2=new LocalAgnTmpNode(tccssan2, ExpressionNodeBuilder.fncall(
				"factorial_r", List_of(lamn)));
		GenLocalAgn(cctx, latn2, gbn);
		
		final TmpSSACtxNode tccssan3 = new TmpSSACtxNode(cctx);
		final LocalAgnTmpNode latn3=new LocalAgnTmpNode(tccssan3, ExpressionNodeBuilder.binex(u64,
				ExpressionNodeBuilder.varref("n", mhn, u64), ExpressionOperators.OP_MULT, tccssan2));
		GenLocalAgn(cctx, latn3, gbn);
		
//		ReturnAgnSimpleIntNode rsin=new ReturnAgnSimpleIntNode(latn3); // TODO Not a simple int
		final ReturnAgnNode rsin=new ReturnAgnNode(latn3); // TODO Not an expression!!!
		GenReturnAgn(cctx, rsin, gbn);
		
		CloseTmpCtx(cctx, latn3, gbn);
/*
		CloseTmpCtx(cctx, latn2, gbn);
		CloseTmpCtx(cctx, lamn, gbn);
*/

		final CloseCaseNode cccn2=new CloseCaseNode(csn2, ChoiceOptions.BREAK, true);
		CloseCaseChoice(cctx, cccn2, gbn);
		
		EndCaseStatement(cctx, shn, gbn);
		EndMeth(cctx, mhn, gbn);

	}

	public void GenMethHdr(final @NotNull CompilerContext cctx, final @NotNull MethHdrNode node, final @NotNull GenBuffer gbn) {
		final TextBuffer buf = gbn.moduleBufHdr(cctx.module());
		buf.append_s(node.returnType().genType());
		buf.append(node.genName());
		buf.append("(");
		for (int c=0;c<node.argCount;c++) {
			buf.append_s(node.argument(c).getGenType());
			buf.append(node.argument(c).getGenName());
			if (c<node.argCount-1) {buf.append(",");}
		}
		buf.append(");");
	}

	private void BeginMeth(final @NotNull CompilerContext cctx, final @NotNull MethHdrNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
/*
		BufferSequenceBuilder sb = new BufferSequenceBuilder(4).
				named("type").named("name").named("args").semieol();
		sb.set("type", node.returnType.genType, XX.SPACE);
		sb.set("name", node.methName.genName);
		EnclosedBuffer sb2 = new EnclosedBuffer("(", XX.RPAREN);
		Transform1 transform1 = new Transform1();
		BufferSequenceBuilder sb3 = new BufferSequenceBuilder(node.argCount,
				node.ArgumentsIterator(), transform1, XX.COMMA, gbn);
		sb2.setPayload(sb3);
		sb.set("args", sb2);
		CodeGen gbm = gbn.getCodeGen(); // TODO should be CSimpleGen
		gbm.appendHeader(cctx.module(), sb.build());
*/
		buf.append_s(node.returnType().genType());
		buf.append(node.genName());
		buf.append("(");
		for (int c=0;c<node.argCount;c++) {
			buf.append_s(node.argument(c).getGenType());
			buf.append(node.argument(c).getGenName());
			if (c<node.argCount-1) {buf.append(",");}
		}
		buf.append_ln(") {");
		buf.incr_i();
		}

	private void BeginCaseStatement(final @NotNull CompilerContext cctx, final @NotNull CaseHdrNode node, final @NotNull GenBuffer gbn) {
		final TextBuffer buf = gbn.moduleBufImpl(cctx.module());
		final boolean is_simple = node.getExpr().is_simple();
		buf.append_s("switch (");
		if (is_simple) {
			buf.append(node.getExpr().genText(cctx));
		} else {
			// TODO implement complex part
		}
		buf.append_nl_i(") {");
		buf.incr_i();
	}

	private void BeginCaseChoice(final @NotNull CompilerContext cctx, final @NotNull CaseChoiceNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		final boolean is_simple = node.left.is_const_expr();
		final boolean is_default = node.is_simple();
		assert !is_default || node.left.is_underscore() ||
				(node.left.is_var_ref() && node.left.ref_ == null);
		if (is_simple ){
			buf.append("case "+node.left.genText(cctx));
			buf.append_s(": {");
		}
		if (is_default) {
			buf.append_nl("default:");
		}
	}

	private void GenReturnAgn(final @NotNull CompilerContext cctx, final @NotNull ReturnAgnNode node, final @NotNull GenBuffer gbn) {
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.append("vsr =");
		buf.append(node.getExpr().genText(cctx));
		buf.append_ln(";");
		// if(node.usesRetKW()) {buf.append ("return vsr;");}
	}

	private void CloseCaseChoice(final @NotNull CompilerContext cctx, final CloseCaseNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf = gbn.moduleBufImpl(cctx.module());
		buf.decr_i();
		buf.append_ln("break; }");
//		buf.append_nl("} // close select ("+node.hdr_node.left.genName+")"); // TODO left was expr
	}

	private void BeginDefaultCaseStatement(final @NotNull CompilerContext cctx, final @NotNull CaseChoiceNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.incr_i();
		buf.append_ln("default: {");
		if (!node.is_default())
			throw new IllegalStateException("node is not default");
		//
		final VariableReferenceNode varref = node.varref();
		if (varref == null)
			throw new IllegalStateException("varref is null");

		{
			buf.append_s(node.header.getExpr().genType());//varref().getType().getText());
			final String string;
			//
			string = varref.genText();
			//
			buf.append_s(string);
			buf.append(" = ");
			buf.append(node.header.simpleGenText());
			buf.append_ln(";");
/*
		} else {
			final VariableReferenceNode3 vr3 = node.varref3();
			buf.append_s(vr3.getType().genType());
			buf.append(vr3.genText());
			buf.append(" = ");
			buf.append(node.header.simpleGenText());
			buf.append_ln(";");
		}
*/
		}
	}

	public static @NotNull TextBuffer BeginTmpSSACtx(final @NotNull CompilerContext cctx, final @NotNull TmpSSACtxNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.incr_i();
		buf.append_ln("{");
		if (node._tmp != null) {
//			NotImplementedException.raise();
//			buf.append(node._tmp.genName());
			buf.append(node._tmp.getRight().genType());
		}else {
			buf.append(node.getType().genText(cctx));
		}
		buf.append(" ");
		return buf;
	}

	public static @NotNull TextBuffer GenLocalAgn(final @NotNull CompilerContext cctx, final @NotNull LocalAgnTmpNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
//		if (node instanceof LocalAgnTmpNode) {
//			BufferSequenceBuilder sb=new BufferSequenceBuilder(6).
//					named("open").named("type").named("name").
//					named("equality").named("value").semieol();
//			sb.set("open", "{", XX.INDENT);
//			sb.set("type", node.getRight().genType, XX.SPACE);
//		}
		buf.append(node.getLeft().genText(cctx));
		buf.append(" = ");
		buf.append(node.getRight().genText(cctx));
		buf.append_ln(";");
		return buf;
	}

	private void CloseTmpCtx(final @NotNull CompilerContext cctx, final LocalAgnTmpNode lamn, final @NotNull GenBuffer gbn) {
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.append_cb(""); // close-brace
	}
	
	private void EndCaseStatement(final @NotNull CompilerContext cctx, final @NotNull CaseHdrNode node, final @NotNull GenBuffer gbn) {
		// TODO Auto-generated method stub
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.decr_i();
		buf.append_nl("} // close select "+node.getExpr().genText());
	}

	private void EndMeth(final @NotNull CompilerContext cctx, final MethHdrNode mhn, final @NotNull GenBuffer gbn) {
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.append_ln("return vsr;");
		buf.decr_i();
		buf.append_ln("}");
	}

	@Deprecated
	private void GenReturnAgnSimpleInt(final @NotNull CompilerContext cctx, final @NotNull ReturnAgnSimpleIntNode rasin, final @NotNull GenBuffer gbn) {
		final TextBuffer buf=gbn.moduleBufImpl(cctx.module());
		buf.append("vsr = ");
		buf.append(((Integer)rasin.getValue()).toString());
		buf.append_nl(";");
	}

	public class Transform1 implements Transform<ArgumentNode> {
		/* (non-Javadoc)
		 * @see tripleo.util.buffer.Transform#transform(tripleo.elijah.gen.nodes.ArgumentNode, tripleo.util.buffer.DefaultBuffer)
		 */
//		@Override
		@Override
		public void transform(final @NotNull ArgumentNode na, final @NotNull DefaultBuffer bufbldr) {
			bufbldr.append_s(na.getGenType(), XX.SPACE);
			bufbldr.append(na.getVarName());
		}
	}
		
}
	
//
//
//

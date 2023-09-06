/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.test_help.Boilerplate;
import tripleo.elijah.util.NotImplementedException;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 3/4/21 3:53 AM
 */
public class TestIdentNormal {

	@Ignore
	@Test(expected = IllegalStateException.class) // TODO proves nothing
	public void test() {

		final FunctionDef fd   = mock(FunctionDef.class);
		final Context     ctx1 = mock(Context.class);
		final Context     ctx2 = mock(Context.class);

		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final GeneratePhase generatePhase = boilerplate.pipelineLogic().generatePhase;

		final GenerateFunctions generateFunctions = new GenerateFunctions(boilerplate.defaultMod(), boilerplate.pipelineLogic, boilerplate.comp.pa());

		final EvaFunction       generatedFunction = new EvaFunction(fd);
		final VariableSequence  seq               = new VariableSequenceImpl(ctx1);
		final VariableStatement vs                = new VariableStatementImpl(seq);
		final IdentExpression   x                 = IdentExpression.forString("x");
		vs.setName(x);
		final IdentExpression         foo = IdentExpression.forString("foo");
		final ProcedureCallExpression pce = new ProcedureCallExpressionImpl();
		pce.setLeft(new DotExpressionImpl(x, foo));

		final InstructionArgument                s = generateFunctions.simplify_expression(pce, generatedFunction, ctx2);
		@NotNull final List<InstructionArgument> l = BaseEvaFunction._getIdentIAPathList(s);
		tripleo.elijah.util.Stupidity.println_out_2(String.valueOf(l));
//      tripleo.elijah.util.Stupidity.println_out_2(generatedFunction.getIdentIAPathNormal());

		//
		//
		//

		final LookupResultList lrl = new LookupResultListImpl();
		lrl.add("x", 1, vs, ctx2);
		when(ctx2.lookup("x")).thenReturn(lrl);

		//
		//
		//

		final IdentIA identIA = new IdentIA(1, generatedFunction);

		final DeducePhase  phase = boilerplate.getDeducePhase();
		final DeduceTypes2 d2    = new DeduceTypes2(boilerplate.defaultMod(), phase);

		final List<InstructionArgument> ss = BaseEvaFunction._getIdentIAPathList(identIA);
		d2.resolveIdentIA2_(ctx2, null, ss/*identIA*/, generatedFunction, new FoundElement(phase) {
			@Override
			public void foundElement(final OS_Element e) {
				System.err.println(e);
			}

			@Override
			public void noFoundElement() {
				NotImplementedException.raise();
			}
		});
	}

	@Ignore
	@Test // TODO just a mess
	public void test2() {
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		final OS_Module mod = boilerplate.defaultMod();
		boilerplate.getGenerateFiles(mod);

		final Context     ctx2  = mock(Context.class);
		final DeducePhase phase = boilerplate.getDeducePhase();

		//
		//
		//

		ClassStatement        cs       = new ClassStatementImpl(mod, mod.getContext());
		final IdentExpression capitalX = IdentExpression.forString("X");
		cs.setName(capitalX);
		FunctionDef fd   = new FunctionDefImpl(cs, cs.getContext());
		Context     ctx1 = fd.getContext();
		fd.setName(IdentExpression.forString("main"));
		FunctionDef fd2 = new FunctionDefImpl(cs, cs.getContext());
		fd2.setName(IdentExpression.forString("foo"));

//		EvaFunction generatedFunction = new EvaFunction(fd);
//		TypeTableEntry tte = generatedFunction.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, new OS_UserType(cs));
//		generatedFunction.addVariableTableEntry("x", VariableTableType.VAR, tte, cs);

		//
		//
		//

		VariableSequence      seq = new VariableSequenceImpl(ctx1);
		VariableStatement     vs  = seq.next();
		final IdentExpression x   = IdentExpression.forString("x");
		vs.setName(x);
		ProcedureCallExpression pce2 = new ProcedureCallExpressionImpl();
		pce2.setLeft(capitalX);
		vs.initial(pce2);
		IBinaryExpression e = ExpressionBuilder.build(x, ExpressionKind.ASSIGNMENT, pce2);

		final IdentExpression   foo = IdentExpression.forString("foo");
		ProcedureCallExpression pce = new ProcedureCallExpressionImpl();
		pce.setLeft(new DotExpressionImpl(x, foo));

		fd.scope(new Scope3Impl(fd));
		fd.add(seq);
		fd.add(new StatementWrapperImpl(pce2, ctx1, fd));
		fd2.scope(new Scope3Impl(fd2));

		final GeneratePhase     generatePhase     = boilerplate.pipelineLogic().generatePhase;
		final GenerateFunctions generateFunctions = boilerplate.pipelineLogic().generatePhase.getGenerateFunctions(mod);

		fd2.add(new StatementWrapperImpl(pce, ctx2, fd2));

		final ClassHeader ch = new ClassHeaderImpl(false, List_of());
		cs.setHeader(ch);

		ClassInvocation    ci   = phase.registerClassInvocation(cs);
		ProcTableEntry     pte2 = null;
		FunctionInvocation fi   = new FunctionInvocation(fd, pte2, ci, generatePhase);
//		when(fd.returnType()).thenReturn(null);
		final FormalArgList formalArgList = new FormalArgListImpl();
//		when(fd.fal()).thenReturn(formalArgList);
//		when(fd.fal()).thenReturn(formalArgList);
//		when(fd2.returnType()).thenReturn(null);
		EvaFunction generatedFunction = generateFunctions.generateFunction(fd, cs, fi);

/*
		InstructionArgument es = generateFunctions.simplify_expression(e, generatedFunction, ctx2);

		InstructionArgument s = generateFunctions.simplify_expression(pce, generatedFunction, ctx2);
*/

		//
		//
		//

		LookupResultList lrl = new LookupResultListImpl();
		lrl.add("foo", 1, fd2, ctx2);

		when(ctx2.lookup("foo")).thenReturn(lrl);

		LookupResultList lrl2 = new LookupResultListImpl();
		lrl2.add("X", 1, cs, ctx1);

		when(ctx2.lookup("X")).thenReturn(lrl2);

		//
		//
		//


		final DeduceTypes2 aDeduceTypes2 = null; // !! 08/28
		ClassInvocation    invocation2   = new ClassInvocation(cs, null, new NULL_DeduceTypes2());
		invocation2 = phase.registerClassInvocation(invocation2);
		ProcTableEntry     pte3               = null;
		FunctionInvocation fi2                = new FunctionInvocation(fd2, pte3, invocation2, generatePhase);
		EvaFunction        generatedFunction2 = generateFunctions.generateFunction(fd2, fd2.getParent(), fi2);//new EvaFunction(fd2);
//		generatedFunction2.addVariableTableEntry("self", VariableTableType.SELF, null, null);
//		final TypeTableEntry type = null;
//		int res = generatedFunction2.addVariableTableEntry("Result", VariableTableType.RESULT, type, null);

		//
		//
		//

		IdentIA identIA = new IdentIA(0, generatedFunction);

		DeduceTypes2 d2 = new DeduceTypes2(mod, phase);

		generatedFunction.getVarTableEntry(0).setConstructable(generatedFunction.getProcTableEntry(0));
		identIA.getEntry().setCallablePTE(generatedFunction.getProcTableEntry(1));

		d2.resolveIdentIA2_(ctx2, identIA, generatedFunction, new FoundElement(phase) {
			@Override
			public void foundElement(OS_Element e) {
				assert e == fd2;
			}

			@Override
			public void noFoundElement() {
				assert false;
			}
		});
	}

}

//
//
//

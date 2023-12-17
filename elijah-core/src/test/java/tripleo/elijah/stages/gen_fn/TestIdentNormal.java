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
import tripleo.elijah.ReadySupplier_1;
import tripleo.elijah.context_mocks.ContextMock;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gdm.GDM_IdentExpression;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.test_help.Boilerplate;
import tripleo.elijah.test_help.XX;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 3/4/21 3:53 AM
 */
public class TestIdentNormal {

	@Ignore
	@Test
	public void test() {
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();

		final OS_Module mod = boilerplate.defaultMod();
		//boilerplate.getGenerateFiles(mod);

		final FunctionDef fd                = mock(FunctionDef.class);
		final Context     ctx2              = new ContextMock();
		final XX          factory           = new XX();
		final EvaFunction generatedFunction = new EvaFunction(fd);

		//

		final IdentExpression   x  = factory.makeIdent("x");
		final VariableStatement vs = factory.sequenceAndVarNamed(x);

		//

		final IdentExpression         foo = factory.makeIdent("foo");
		final ProcedureCallExpression pce = factory.makeDottedProcedureCall(x, foo);

		//

		final GenerateFunctions                  generateFunctions = boilerplate.defaultGenerateFunctions();
		final GFS_ProcedureCall                  gfs               = generateFunctions.scheme(pce, generatedFunction, ctx2);
		final @NotNull List<InstructionArgument> l                 = gfs.getIdentIAPathList();
		SimplePrintLoggerToRemoveSoon.println_out_2("8999-66" + String.valueOf(l));
//      tripleo.elijah.util.Stupidity.println_out_2(generatedFunction.getIdentIAPathNormal());

		//

		ctx2.expect(x.getText(), vs).andContributeResolve(ctx2);

		//

		final IdentIA identIA = new IdentIA(1, generatedFunction);

		final DeducePhase  phase = boilerplate.getDeducePhase();
		final DeduceTypes2 d2    = boilerplate.defaultDeduceTypes2(mod);

		final List<InstructionArgument> ss       = BaseEvaFunction._getIdentIAPathList(identIA);

		var          xx = generatedFunction._getIdentIAResolvable(identIA);
		System.err.println("8585 "+xx.getNormalPath(generatedFunction, identIA));


		final GDM_IdentExpression gdm = generateFunctions.monitor(x);
		boilerplate.fixTables(gdm, mod, generatedFunction);
		final GDM_IdentExpression gdm_foo = generateFunctions.monitor(foo);
		boilerplate.fixTables(gdm_foo, mod, generatedFunction);

		final boolean[]                 ss_found = {false};
		final FoundElement foundElement = new FoundElement(phase) {
			@Override
			public void foundElement(final OS_Element e) {
				System.err.println("8999-87 " + e);
				ss_found[0] = true;
			}

			@Override
			public void noFoundElement() {
//				NotImplementedException.raise();
				ss_found[0] = false;
			}
		};

		//gdm.trigger_resolve(ctx2, ss, foundElement, d2, generatedFunction);
		gdm_foo.trigger_resolve(ctx2, ss, foundElement, d2, generatedFunction);

		assertTrue(ss_found[0]);
	}

	@Ignore
	@Test // TODO just a mess
	public void test2() {
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		final OS_Module mod = boilerplate.defaultMod();
		boilerplate.getGenerateFiles(mod);

		final Context     ctx2  = new ContextMock();
		final DeducePhase phase = boilerplate.getDeducePhase();

		//
		//
		//

		mod.setContext(new ModuleContext(mod));

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

		final Scope3Impl sco = new Scope3Impl(fd);
		sco.add(seq);
		sco.add(new StatementWrapperImpl(pce2, ctx1, fd));
		fd.scope(sco);

		final Scope3Impl sco1 = new Scope3Impl(fd2);

		final GeneratePhase     generatePhase     = boilerplate.pipelineLogic().generatePhase;
		final GenerateFunctions generateFunctions = boilerplate.pipelineLogic().generatePhase.getGenerateFunctions(mod);

		sco1.add(new StatementWrapperImpl(pce, ctx2, fd2));
		fd2.scope(sco1);

		final ClassHeader ch = new ClassHeaderImpl(false, List_of());
		ch.setName(capitalX);
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

/*
		LookupResultList lrl = new LookupResultListImpl();
		lrl.add("foo", 1, fd2, ctx2);

		when(ctx2.lookup("foo")).thenReturn(lrl);
*/
		ctx2.expect("foo", fd2).andContributeResolve(ctx2);

/*
		LookupResultList lrl2 = new LookupResultListImpl();
		lrl2.add("X", 1, cs, ctx1);

		when(ctx2.lookup("X")).thenReturn(lrl2);
*/
		ctx2.expect("X", cs).andContributeResolve(ctx1);

		//
		//
		//

		DeduceTypes2 d2 = new DeduceTypes2(new DeduceTypes2Request(mod, phase, ElLog.Verbosity.VERBOSE));


		ClassInvocation    invocation2   = new ClassInvocation(cs, null, new ReadySupplier_1<>(d2));
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


		generatedFunction.getVarTableEntry(0).setConstructable(generatedFunction.getProcTableEntry(0));
		identIA.getEntry().setCallablePTE(generatedFunction.getProcTableEntry(1));

		@NotNull FoundElement foundElement = new FoundElement(phase) {
			@Override
			public void foundElement(OS_Element e) {
				assert e == fd2;
			}

			@Override
			public void noFoundElement() {
				assert false;
			}
		};

		final @NotNull List<InstructionArgument> s = BaseEvaFunction._getIdentIAPathList(identIA);

		final GDM_IdentExpression mix = generateFunctions.monitor(x);
		boilerplate.fixTables(mix, mod, generatedFunction);
		mix.trigger_resolve(ctx2, s, foundElement, d2, generatedFunction);

		//d2.resolveIdentIA2_(ctx2, identIA, s, generatedFunction, foundElement);
	}

}

//
//
//

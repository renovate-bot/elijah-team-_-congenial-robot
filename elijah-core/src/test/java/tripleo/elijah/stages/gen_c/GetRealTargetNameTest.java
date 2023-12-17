/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.stages.gen_c;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.context_mocks.ContextMock;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.ClassStatementImpl;
import tripleo.elijah.lang.impl.FunctionDefImpl;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.VariableTableType;
import tripleo.elijah.test_help.Boilerplate;
import tripleo.elijah.test_help.XX;

public class GetRealTargetNameTest {
	private EvaFunction gf;
	private OS_Module   mod;
	private Boilerplate boilerPlate; // NOTE hmm. (reduce) boilerplate reductionism

	@Before
	public void setUp() throws Exception {
		//mod = mock(OS_Module.class);
		//FunctionDef fd = mock(FunctionDef.class);

		final OS_Module      mod2 = new OS_ModuleImpl();
		final ModuleContext  ctx  = new ModuleContext(mod2);
		final ClassStatement cs   = new ClassStatementImpl(mod2, ctx);


		FunctionDef fd = new FunctionDefImpl(cs, ctx);
		gf = new EvaFunction(fd);

		boilerPlate = new Boilerplate();
		boilerPlate.get();
	}

	@Ignore
	@Test // too complicated
	public void testManualXDotFoo() {
		Emit.emitting = false;

		final XX              factory = new XX();
		final IdentExpression x_ident = factory.makeIdent("x");

		final Context foo_ctx = new ContextMock();

		final IdentExpression foo_ident = factory.makeIdent("foo", foo_ctx);

		//
		// create x.foo, where x is a VAR and foo is unknown
		// neither has type information
		// GenerateC#getRealTargetName doesn't use type information
		// TODO but what if foo was a property instead of a member
		//
		final TypeTableEntry    tte       = factory.regularTypeName_specifyTableEntry(x_ident, gf, "X_Type");
		final VariableStatement x_var     = factory.sequenceAndVarNamed(x_ident);
		final int               int_index = gf.addVariableTableEntry("x", VariableTableType.VAR, tte, x_var);
		final int               ite_index = gf.addIdentTableEntry(foo_ident, null);
		final IdentIA           ident_ia  = new IdentIA(ite_index, gf);
		final IntegerIA         integerIA = new IntegerIA(int_index, gf);
		ident_ia.setPrev(integerIA);

		final Context ctx = new ContextMock();
		// TODO 11/08 specify times can be called as well? (as was with Mockito)
		ctx.expect(x_ident.getText(), x_var).andContributeResolve(null);

		final OS_Module   mod   = boilerPlate.defaultMod();

		//ident_ia.getEntry().setDeduceTypes2(deduceTypes2, foo_ctx, gf); // TODO 11/08 doesn't work??
		ident_ia.getEntry()._fix_table(boilerPlate.defaultDeduceTypes2(mod), gf);

		//final GenType genType = factory.makeGenType(tte);
		//integerIA.getEntry().resolveType(genType);

		//
		//
		//

		boilerPlate.getGenerateFiles(mod);

		String x = ((GenerateC) boilerPlate.generateFiles).getRealTargetName(gf, ident_ia, Generate_Code_For_Method.AOG.GET, null);
		Assert.assertEquals("vvx->vmfoo", x);
	}
}

//
//
//

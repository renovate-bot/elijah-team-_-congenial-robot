/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.test_help.Boilerplate;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.contexts.FunctionContext;
import tripleo.elijah_durable_congenial.contexts.ModuleContext;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang.impl.*;
import tripleo.elijah_durable_congenial.lang.types.OS_BuiltinType;
import tripleo.elijah_durable_congenial.lang.types.OS_UserType;
import tripleo.elijah_durable_congenial.lang2.BuiltInTypes;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceLookupUtils;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.ResolveError;
import tripleo.elijah_durable_congenial.stages.deduce.Resolve_Ident_IA.DeduceElementIdent;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;
import tripleo.elijah_durable_congenial.util.Helpers;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

import static org.mockito.Mockito.mock;
import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

/**
 * Useless tests. We really want to know if a TypeName will resolve to the same types
 */
@Ignore
public class DeduceTypesTest {

	private GenType x;

	@Before
	public void setUp() {
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final OS_Module     mod  = boilerplate.defaultMod();
		final ModuleContext mctx = new ModuleContext(mod);
		mod.setContext(mctx);

		final ClassStatement cs = new ClassStatementImpl(mod, mod.getContext());
		final ClassHeader    ch = new ClassHeaderImpl(false, List_of());
		ch.setName(Helpers.string_to_ident("Test"));
		cs.setHeader(ch);
		final FunctionDef fd = cs.funcDef();
		fd.setName(Helpers.string_to_ident("test"));
		final Scope3           scope3 = new Scope3Impl(fd);
		final VariableSequence vss    = scope3.statementClosure().varSeq(fd.getContext());
		final VariableStatement vs      = vss.next();
		final IdentExpression   x_ident = Helpers.string_to_ident("x");
		x_ident.setContext(fd.getContext());
		vs.setName(x_ident);
		final Qualident qu = new QualidentImpl();
		qu.append(Helpers.string_to_ident("Integer"));
		((NormalTypeName) vs.typeName()).setName(qu);
		vs.typeName().setContext(fd.getContext());
		fd.scope(scope3);
		fd.postConstruct();
		cs.postConstruct();
		mod.postConstruct();
		final FunctionContext fc = (FunctionContext) fd.getContext(); // TODO needs to be mocked
		final IdentExpression x1 = Helpers.string_to_ident("x");
		x1.setContext(fc);

		final ElLog.Verbosity verbosity = Compilation.gitlabCIVerbosity();
		final DeducePhase     dp        = boilerplate.getDeducePhase();
		final DeduceTypes2    d         = dp.deduceModule(new DeducePhase_deduceModule_Request(mod, dp.generatedClasses, verbosity, dp));
		final BaseEvaFunction bgf       = mock(BaseEvaFunction.class);
		final IdentTableEntry                ite       = new IdentTableEntry(0, x1, x1.getContext(), bgf);
		final DeduceElementIdent             dei       = new DeduceElementIdent(ite); // TODO 12/24 This is here to say why is this here?
		final DeduceElement3_IdentTableEntry de3_ite   = ite.getDeduceElement3(d, bgf);

		final Operation2<WorldModule> fpl0 = boilerplate.comp.findPrelude("c");
		assert fpl0.mode() == Mode.SUCCESS;
		//final Operation2<OS_Module>   fpl  = boilerplate.comp.findPrelude("c");
		mod.setPrelude(fpl0.success().module());

		final Eventual<DeduceElement3_IdentTableEntry> entryEventual = DeduceLookupUtils.deduceExpression2(de3_ite, fc);

		entryEventual.then(xxx -> {
			this.x = xxx.genType();
			SimplePrintLoggerToRemoveSoon.println_out_2(String.valueOf(this.x));
		});
		entryEventual.onFail(fail -> {
			// TODO 12/24 nop for now
		});
	}

	/**
	 * TODO This test fails beacause we are comparing a BUILT_IN vs a USER OS_Type.
	 *   It fails because Integer is an interface and not a BUILT_IN
	 */
	@Test(expected = ResolveError.class)
	public void testDeduceIdentExpression1() {
		final BuiltInTypes bi_integer = new OS_BuiltinType(BuiltInTypes.SystemInteger).getBType();
		final BuiltInTypes inferred_t = x.getResolved().getBType();

		Assert.assertEquals(bi_integer, inferred_t);
	}

	/**
	 * Now comparing {@link RegularTypeName} to {@link VariableTypeName} works
	 */
	@Test
	public void testDeduceIdentExpression2() {
		final RegularTypeName tn  = new RegularTypeNameImpl();
		final Qualident       tnq = new QualidentImpl();
		tnq.append(Helpers.string_to_ident("Integer"));
		tn.setName(tnq);
		Assert.assertTrue(genTypeTypenameEquals(new OS_UserType(tn), x/*.getTypeName()*/));
	}

	private boolean genTypeTypenameEquals(OS_Type aType, @NotNull GenType genType) {
		return genType.getTypeName().equals(aType);
	}

	@Test
	public void testDeduceIdentExpression3() {
		final VariableTypeName tn  = new VariableTypeNameImpl();
		final Qualident        tnq = new QualidentImpl();
		tnq.append(Helpers.string_to_ident("Integer"));
		tn.setName(tnq);
		Assert.assertEquals(new OS_UserType(tn).getTypeName(), x.getTypeName().getTypeName());
		Assert.assertTrue(genTypeTypenameEquals(new OS_UserType(tn), x));
	}

	@Test
	public void testDeduceIdentExpression4() {
		final VariableTypeName tn  = new VariableTypeNameImpl();
		final Qualident        tnq = new QualidentImpl();
		tnq.append(Helpers.string_to_ident("Integer"));
		tn.setName(tnq);
		Assert.assertEquals(new OS_UserType(tn).getTypeName(), x.getTypeName().getTypeName());
		Assert.assertTrue(genTypeTypenameEquals(new OS_UserType(tn), x));
		Assert.assertEquals(new OS_UserType(tn).toString(), x.getTypeName().toString());
	}

}

//
//
//

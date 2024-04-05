///*
// * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
// *
// * The contents of this library are released under the LGPL licence v3,
// * the GNU Lesser General Public License text was downloaded from
// * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
// *
// */
//package tripleo.elijah.stages.gen_fn;
//
//import org.jdeferred2.impl.DeferredObject;
//import org.jetbrains.annotations.Contract;
//import org.jetbrains.annotations.NotNull;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import tripleo.elijah.comp.*;
//import tripleo.elijah.comp.Compilation.CompilationAlways;
//import tripleo.elijah.comp.i.CompilationEnclosure;
//import tripleo.elijah.comp.i.CompilationFlow;
//import tripleo.elijah.comp.i.ErrSink;
//import tripleo.elijah.comp.impl.DefaultCompilationFlow;
//import tripleo.elijah.comp.internal.CompilationImpl;
//import tripleo.elijah.comp.internal.CompilationRunner;
//import tripleo.elijah.comp.internal.DefaultCompilationAccess;
//import tripleo.elijah.comp.internal.DefaultCompilerController;
//import tripleo.elijah.factory.comp.CompilationFactory;
//import tripleo.elijah.lang.i.ClassStatement;
//import tripleo.elijah.lang.i.FunctionDef;
//import tripleo.elijah.lang.i.OS_Module;
//import tripleo.elijah.lang.i.OS_Type;
//import tripleo.elijah.nextgen.query.Mode;
//import tripleo.elijah.stages.deduce.DeducePhase;
//import tripleo.elijah.stages.deduce.DeduceTypes2;
//import tripleo.elijah.stages.deduce.IFunctionMapHook;
//import tripleo.elijah.stages.gen_c.GenerateC;
//import tripleo.elijah.stages.gen_generic.GenerateResult;
//import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
//import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
//import tripleo.elijah.stages.instructions.Instruction;
//import tripleo.elijah.stages.instructions.InstructionName;
//import tripleo.elijah.stages.logging.ElLog;
//import tripleo.elijah.test_help.Boilerplate;
//import tripleo.elijah.util.Operation;
//import tripleo.elijah.work.WorkManager;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static org.junit.Assert.assertSame;
//import static tripleo.elijah.util.Helpers.List_of;
//
///**
// * Created 9/10/20 2:20 PM
// */
//public class TestGenFunction {
//
//	@Contract(value = " -> new", pure = true)
//	public static CompilationFlow.@NotNull CompilationFlowMember parseElijah() {
//		return new CompilationFlow.CompilationFlowMember() {
//			@Override
//			public void doIt(final Compilation cc, final CompilationFlow flow) {
//				int y = 2;
//			}
//		};
//	}
//
//	@Ignore
//	@Test
//	@SuppressWarnings("JUnit3StyleTestMethodInJUnit4Class")
//	public void testDemoElNormalFact1Elijah() throws Exception {
//		//final StdErrSink  eee = new StdErrSink();
//		//final Compilation c   = CompilationFactory.mkCompilation(eee, new IO());
//
//
//		final String f = "test/demo-el-normal/fact1.elijah";
//
//		final File file = new File(f);
//
//		final CompilerInput ci_f = new CompilerInput(f); // TODO flesh this out
//		final TGF_State     st   = new TGF_State();
//		st.inputs = List_of(ci_f);
//
//		final CompilationFlow flow = new DefaultCompilationFlow();
//
//		flow.add(new CompilationFlow.CompilationFlowMember() {
//			@Override
//			public void doIt(final Compilation cc, final CompilationFlow flow) {
////				final Operation<OS_Module> om;
////				try {
////					// TODO model as query?
////					om = cc.use.realParseElijjahFile(f, file, false);
////				} catch (Exception aE) {
////					throw new RuntimeException(aE);
////				}
////				assertTrue(om.mode() == Mode.SUCCESS);
////				st.m          = om.success();
//			}
//		});
//		flow.add(new CompilationFlow.CF_FindPrelude(x -> {
//			st.m.setPrelude(x.success());
//		}));
//		//flow.add(CompilationFlow.parseElijah());
//		flow.add(new CF_FindPrelude(st));
//		flow.add(new CF_GenFromEntrypoints());
//		flow.add(new CF_GetClasses(st));
//		flow.add(new CF_RunFunctionMapHooks(st));
//		flow.add(new CF_DeduceModuleWithClasses(st));
//		flow.add(new CF_FinishModule());
//		flow.add(new CF_ReturnErrorCount());
//
//		//st.c.fakeFlow(List_of(ci_f), flow);
//
//		//st.p.then(ccc->flow.run((CompilationImpl) ccc));
//
//		CompilationImpl cc = CompilationFactory.mkCompilation(new StdErrSink(), new IO());
//		flow.run(cc);
//
//
//		Assert.assertEquals("Not all hooks ran", 4, st.ran_hooks.size());
//		Compilation c =null;
//		Assert.assertEquals(16, c.errorCount());
//	}
//
//	@Test
//	@SuppressWarnings("JUnit3StyleTestMethodInJUnit4Class")
//	public void testGenericA() {
//		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());
//
//		final String f = "test/basic1/genericA/";
//		c.feedInputs(List_of(new CompilerInput(f)), new DefaultCompilerController());
//	}
//
//	@Test
//	public void testBasic1Backlink3Elijah() {
//		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());
//
//		final String ff = "test/basic1/backlink3/";
//		c.feedInputs(List_of(new CompilerInput(ff)), new DefaultCompilerController());
//	}
//
//	//@Ignore
//	@Test // ignore because of generateAllTopLevelClasses
//	@SuppressWarnings("JUnit3StyleTestMethodInJUnit4Class")
//	public void testBasic1Backlink1Elijah() throws Exception {
//		Boilerplate boilerplate = new Boilerplate();
//		boilerplate.get();
//		boilerplate.getGenerateFiles(boilerplate.defaultMod());
//
//		var state = new TGF_State();
//		var flow  = new TGF_Flow();
//
//		flow.setCompilation(boilerplate.comp);
//
//		final Compilation c = boilerplate.comp;//CompilationFactory.mkCompilation(eee, new IO());
//
//		final String               f  = "test/basic1/backlink1.elijah";
//		final Operation<OS_Module> om = flow.addElijahFile(f);
//
//		assertSame("Method parsed correctly", om.mode(), Mode.SUCCESS);
//
//		final OS_Module m = om.success();
//
//		flow.setDefaultPrelude(m);
//		//flow.setDefaultCompilationAccess();
//
//		final WorkManager wm = new WorkManager();
//
//		final List<EvaNode> lgc = flow.generateAllTopLevelClasses(m);
//		state.lgc = lgc;
//
//		@NotNull List<EvaNode> lgf = flow.extractFunctions(lgc);
//
//		//here
//		flow.printInstructions(lgf);
//
//		flow.deduceModule(m, lgc, lgf);
//
//		//here
//		flow.printFunctionTables(lgf);
//
//		var gp = new EvaPipeline(boilerplate.comp.get_pa());
//
//		generateCode result = flow.getGenerateCode(m, wm, lgf, gp);
//
//		flow.generateResults(lgc, result);
//	}
//
//	private record generateCode(GenerateC ggc, DefaultGenerateResultSink grs) {
//	}
//
//	static class TGF_Flow {
//
//		private Compilation  comp;
//		private GeneratePhase generatePhase;
//
//		private void generateResults(final @NotNull List<EvaNode> lgc, final @NotNull generateCode result) {
//			GenerateResult gr = new Old_GenerateResult();
//
//			final DefaultGenerateResultSink grs = result.grs();
//			final GenerateC                 ggc = result.ggc();
//
//			for (EvaNode generatedNode : lgc) {
//				if (generatedNode instanceof final EvaClass evaClass) {
//					ggc.generate_class(evaClass, gr, grs);
//				} else {
//					tripleo.elijah.util.Stupidity.println_out_2(lgc.getClass().getName());
//				}
//			}
//		}
//
//		@NotNull
//		private generateCode getGenerateCode(final OS_Module m,
//											 final WorkManager wm,
//											 final @NotNull List<EvaNode> lgf,
//											 final EvaPipeline aGp) {
//			final ErrSink                   errSink   = comp.getErrSink();
//			final ElLog.Verbosity           verbosity = Compilation.gitlabCIVerbosity();
//			final CompilationEnclosure      ce        = comp.getCompilationEnclosure();
//			final DefaultGenerateResultSink grs       = aGp.grs();//new DefaultGenerateResultSink(aGp, comp.get_pa());
//
//			// TODO look in Boilerplate
//			final GenerateC ggc = new GenerateC(m, errSink, verbosity, ce);
//			ggc.generateCode(lgf, wm, grs, aFileGen);
//			generateCode result = new generateCode(ggc, grs);
//			return result;
//		}
//
//		private void printFunctionTables(final @NotNull List<EvaNode> lgf) {
//			for (final EvaNode gn : lgf) {
//				if (gn instanceof final EvaFunction gf) {
//					tripleo.elijah.util.Stupidity.println_out_2("----------------------------------------------------------");
//					tripleo.elijah.util.Stupidity.println_out_2(gf.name());
//					tripleo.elijah.util.Stupidity.println_out_2("----------------------------------------------------------");
//					EvaFunction.printTables(gf);
//					tripleo.elijah.util.Stupidity.println_out_2("----------------------------------------------------------");
//				}
//			}
//		}
//
//		private void deduceModule(final OS_Module m,
//								  final List<EvaNode> lgc,
//								  final @NotNull List<EvaNode> lgf) {
//			final DeducePhase  dp  = comp.getCompilationEnclosure().getPipelineLogic().dp;
//			final DeduceTypes2 dt2 = dp.deduceModule(m, lgc, Compilation.gitlabCIVerbosity());
//			dt2.deduceFunctions(lgf);
//			dp.finish();
//		}
//
//		private void printInstructions(final @NotNull List<EvaNode> lgf) {
//			for (final EvaNode gn : lgf) {
//				if (gn instanceof final EvaFunction gf) {
//					for (final Instruction instruction : gf.instructions()) {
//						tripleo.elijah.util.Stupidity.println_out_2("8100 " + instruction);
//					}
//				}
//			}
//		}
//
//		private @NotNull List<EvaNode> extractFunctions(final @NotNull List<EvaNode> lgc) {
//			final List<EvaNode> lgf = new ArrayList<>();
//			for (EvaNode generatedNode : lgc) {
//				if (generatedNode instanceof EvaClass)
//					lgf.addAll(((EvaClass) generatedNode).functionMap.values());
//				if (generatedNode instanceof EvaNamespace)
//					lgf.addAll(((EvaNamespace) generatedNode).functionMap.values());
//				// TODO enum
//			}
//			return lgf;
//		}
//
//		public void setCompilation(final Compilation aComp) {
//			comp = aComp;
//		}
//
//		public Operation<OS_Module> addElijahFile(final String f) {
//			// SourcefileRequest (/Query??)
//			final File file = new File(f);
//
//			return comp.use().realParseElijjahFile(comp.con().createInputRequest(file, comp.cfg().do_out));
//		}
//
//		public void setDefaultPrelude(final OS_Module m) {
//			m.setPrelude(comp.findPrelude(CompilationAlways.defaultPrelude()).success()); // TODO we dont know which prelude to find yet
//		}
//
//		public void setDefaultCompilationAccess() {
//			final DefaultCompilationAccess ca = new DefaultCompilationAccess(comp);
//			comp.getCompilationEnclosure().setCompilationAccess(ca);
//		}
//
//		public List<EvaNode> generateAllTopLevelClasses(final OS_Module m) {
//			final PipelineLogic pl = comp.getCompilationEnclosure().getPipelineLogic();
//
//			final GenerateFunctions gfm = pl.generatePhase.getGenerateFunctions(m);
//			final List<EvaNode>     lgc = new ArrayList<>();
//			gfm.generateAllTopLevelClasses(lgc);
//			return lgc;
//		}
//
//		private record extractFunctions(WorkManager wm, List<EvaNode> lgf) {
//		}
//	}
//
//	static class TGF_State {
//		public    DeducePhase       dp;
//		public    Iterable<EvaNode> lgc;
//		public    OS_Module         m;
//		public List<CompilerInput> inputs;
//		public CompilationImpl c;
//		protected ClassStatement        main_class;
//
//		final DeferredObject<Compilation, Void, Void> p = new DeferredObject<>();
//
//		List<IFunctionMapHook> ran_hooks = new ArrayList<>();
//	}
//
//	private static class CF_FindPrelude implements CompilationFlow.CompilationFlowMember {
//		private final TGF_State st;
//
//		public CF_FindPrelude(final TGF_State aSt) {
//			st = aSt;
//		}
//
//		@Override
//		public void doIt(final Compilation cc, final CompilationFlow flow) {
////			st.main_class = (ClassStatement) st.m.findClass("Main");
////			assert st.main_class != null;
////			st.m.entryPoints = List_of(new MainClassEntryPoint((ClassStatement) st.main_class));
//		}
//	}
//
//	private static class CF_GenFromEntrypoints implements CompilationFlow.CompilationFlowMember {
//		@Override
//		public void doIt(final Compilation cc, final CompilationFlow flow) {
//
//		}
//	}
//
//	private static class CF_GetClasses implements CompilationFlow.CompilationFlowMember {
//		private final TGF_State st;
//
//		public CF_GetClasses(final TGF_State aSt) {
//			st = aSt;
//		}
//
//		@Override
//		public void doIt(final Compilation cc, final CompilationFlow flow) {
//			DefaultCompilerController dcc = new DefaultCompilerController() {
//				@Override
//				public void hook(final CompilationRunner cr) {
//					st.c = (CompilationImpl) cr._compilation;
//					st.p.resolve(cr._compilation);
//				}
//			};
//			dcc._setInputs(cc, st.inputs);
//			dcc.processOptions();
//			dcc.runner();
//		}
//	}
//
//	private static class CF_RunFunctionMapHooks implements CompilationFlow.CompilationFlowMember {
//		private final TGF_State st;
//
//		public CF_RunFunctionMapHooks(final TGF_State aSt) {
//			st = aSt;
//		}
//
//		@Override
//		public void doIt(final Compilation _cc, final CompilationFlow flow) {
//
//			st.p.then(cc -> {
//				cc.addFunctionMapHook(new IFunctionMapHook() {
//					@Override
//					public boolean matches(FunctionDef fd) {
//						final boolean b = fd.name().equals("main") && fd.getParent() == st.main_class;
//						return b;
//					}
//
//					@Override
//					public void apply(Collection<EvaFunction> aGeneratedFunctions) {
//						assert aGeneratedFunctions.size() == 1;
//
//						EvaFunction gf = aGeneratedFunctions.iterator().next();
//
//						int pc = 0;
//						Assert.assertEquals(InstructionName.E, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.DECL, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.AGNK, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.DECL, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.AGN, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.CALL, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.X, gf.getInstruction(pc).getName());
//						pc++;
//
//						st.ran_hooks.add(this);
//					}
//				});
//
//				cc.addFunctionMapHook(new IFunctionMapHook() {
//					@Override
//					public boolean matches(FunctionDef fd) {
//						final boolean b = fd.name().equals("factorial") && fd.getParent() == st.main_class;
//						return b;
//					}
//
//					@Override
//					public void apply(Collection<EvaFunction> aGeneratedFunctions) {
//						assert aGeneratedFunctions.size() == 1;
//
//						EvaFunction gf = aGeneratedFunctions.iterator().next();
//
//						int pc = 0;
//						Assert.assertEquals(InstructionName.E, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.DECL, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.AGNK, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.ES, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.DECL, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.AGNK, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.JE, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.CALLS, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.CALLS, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.JMP, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.XS, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.AGN, gf.getInstruction(pc).getName());
//						pc++;
//						Assert.assertEquals(InstructionName.X, gf.getInstruction(pc).getName());
//						pc++;
//
//						st.ran_hooks.add(this);
//					}
//				});
//
//				cc.addFunctionMapHook(new IFunctionMapHook() {
//					@Override
//					public boolean matches(FunctionDef fd) {
//						final boolean b = fd.name().equals("main") && fd.getParent() == st.main_class;
//						return b;
//					}
//
//					@Override
//					public void apply(Collection<EvaFunction> aGeneratedFunctions) {
//						assert aGeneratedFunctions.size() == 1;
//
//						EvaFunction gf = aGeneratedFunctions.iterator().next();
//
//						tripleo.elijah.util.Stupidity.println_out_2("main\n====");
//						for (int i = 0; i < gf.vte_list.size(); i++) {
//							final VariableTableEntry vte = gf.getVarTableEntry(i);
//							tripleo.elijah.util.Stupidity.println_out_2(String.format("8007 %s %s %s", vte.getName(), vte.getType(), vte.potentialTypes()));
//							if (vte.getType().getAttached() != null) {
//								Assert.assertNotEquals(OS_Type.Type.BUILT_IN, vte.getType().getAttached().getType());
//								Assert.assertNotEquals(OS_Type.Type.USER, vte.getType().getAttached().getType());
//							}
//						}
//						tripleo.elijah.util.Stupidity.println_out_2("");
//
//						st.ran_hooks.add(this);
//					}
//				});
//
//				cc.addFunctionMapHook(new IFunctionMapHook() {
//					@Override
//					public boolean matches(FunctionDef fd) {
//						final boolean b = fd.name().equals("factorial") && fd.getParent() == st.main_class;
//						return b;
//					}
//
//					@Override
//					public void apply(Collection<EvaFunction> aGeneratedFunctions) {
//						assert aGeneratedFunctions.size() == 1;
//
//						EvaFunction gf = aGeneratedFunctions.iterator().next();
//
//						tripleo.elijah.util.Stupidity.println_out_2("factorial\n=========");
//						for (int i = 0; i < gf.vte_list.size(); i++) {
//							final VariableTableEntry vte = gf.getVarTableEntry(i);
//							tripleo.elijah.util.Stupidity.println_out_2(String.format("8008 %s %s %s", vte.getName(), vte.getType(), vte.potentialTypes()));
//							if (vte.getType().getAttached() != null) {
//								Assert.assertNotEquals(OS_Type.Type.BUILT_IN, vte.getType().getAttached().getType());
//								Assert.assertNotEquals(OS_Type.Type.USER, vte.getType().getAttached().getType());
//							}
//						}
//						tripleo.elijah.util.Stupidity.println_out_2("");
//
//						st.ran_hooks.add(this);
//					}
//				});
//
//				//((CompilationImpl)cc).testMapHooks(cc.pipelineLogic.dp.functionMapHooks);
//
//			});
//		}
//	}
//
//	private static class CF_DeduceModuleWithClasses implements CompilationFlow.CompilationFlowMember {
//		private final TGF_State st;
//
//		public CF_DeduceModuleWithClasses(final TGF_State aSt) {
//			st = aSt;
//		}
//
//		@Override
//		public void doIt(final Compilation cc, final CompilationFlow flow) {
//			st.dp.deduceModule(st.m, st.lgc, Compilation.gitlabCIVerbosity());
//			st.dp.finish();
//		}
//	}
//
//	private static class CF_FinishModule implements CompilationFlow.CompilationFlowMember {
//		@Override
//		public void doIt(final Compilation cc, final CompilationFlow flow) {
//
//		}
//	}
//
//	private static class CF_ReturnErrorCount implements CompilationFlow.CompilationFlowMember {
//		@Override
//		public void doIt(final Compilation cc, final CompilationFlow flow) {
//
//		}
//	}
//}
//
////
////
////

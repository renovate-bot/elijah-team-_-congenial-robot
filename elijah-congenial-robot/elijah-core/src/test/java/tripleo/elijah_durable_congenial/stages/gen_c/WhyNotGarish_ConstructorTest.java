//package tripleo.elijah_durable_congenial.stages.gen_c;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import tripleo.elijah.work.WorkList;
//import tripleo.elijah.work.WorkManager;
//import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
//import tripleo.elijah_durable_congenial.comp.notation.GM_GenerateModule;
//import tripleo.elijah_durable_congenial.comp.notation.GM_GenerateModuleRequest;
//import tripleo.elijah_durable_congenial.comp.notation.GN_GenerateNodesIntoSink;
//import tripleo.elijah_durable_congenial.comp.notation.GN_GenerateNodesIntoSinkEnv;
//import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleList;
//import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
//import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
//import tripleo.elijah_durable_congenial.stages.gen_fn.EvaConstructor;
//import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
//import tripleo.elijah_durable_congenial.stages.gen_generic.Old_GenerateResult;
//import tripleo.elijah_durable_congenial.stages.logging.ElLog;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WhyNotGarish_ConstructorTest {
//
//	@Mock
//	private EvaConstructor mockAGf;
//	@Mock
//	private GenerateC      mockAGenerateC;
//
//	private WhyNotGarish_Constructor whyNotGarishConstructorUnderTest;
//
//	@Before
//	public void setUp() {
//		whyNotGarishConstructorUnderTest = new WhyNotGarish_Constructor(mockAGf, mockAGenerateC);
//	}
//
//	@Test
//	public void testOnFileGen() {
//		// Setup
//		final Old_GenerateResult oldGenerateResult = new Old_GenerateResult();
//		oldGenerateResult.outputFiles(val -> {
//		});
//		final Old_GenerateResult oldGenerateResult1 = new Old_GenerateResult();
//		oldGenerateResult1.outputFiles(val -> {
//		});
//		final GenerateResultEnv aFileGen = new GenerateResultEnv(null, null, new WorkManager(), new WorkList(),
//																 new GM_GenerateModule(new GM_GenerateModuleRequest(
//																		 new GN_GenerateNodesIntoSink(
//																				 new GN_GenerateNodesIntoSinkEnv(
//																						 List.of(), null,
//																						 new EIT_ModuleList(List.of()),
//																						 ElLog.Verbosity.SILENT,
//																						 oldGenerateResult, null,
//																						 new CompilationEnclosure(
//																								 null))), null,
//																		 new GN_GenerateNodesIntoSinkEnv(List.of(),
//																										 null,
//																										 new EIT_ModuleList(
//																												 List.of()),
//																										 ElLog.Verbosity.SILENT,
//																										 oldGenerateResult1,
//																										 null,
//																										 new CompilationEnclosure(
//																												 null)))));
//		when(mockAGenerateC.elLog()).thenReturn(new ElLog("aFileName", ElLog.Verbosity.SILENT, "aPhase"));
//		when(mockAGenerateC.deduced(any(EvaConstructor.class))).thenReturn(null);
//
//		// Configure EvaConstructor.reactive(...).
//		final BaseEvaFunction.__Reactive mock__Reactive = mock(BaseEvaFunction.__Reactive.class);
//		when(mockAGf.reactive()).thenReturn(mock__Reactive);
//
//		// Run the test
//		whyNotGarishConstructorUnderTest.onFileGen(aFileGen);
//
//		// Verify the results
//	}
//
//	@Test
//	public void testGetGenerateC() {
//		// Setup
//		// Run the test
//		final Optional<GenerateC> result = whyNotGarishConstructorUnderTest.getGenerateC();
//
//		// Verify the results
//	}
//
//	@Ignore
//	@Test
//	public void testGetConstructorNameText() {
//		// Setup
//		when(mockAGf.getFD()).thenReturn(null);
//
//		// Run the test
//		final String result = whyNotGarishConstructorUnderTest.getConstructorNameText();
//
//		// Verify the results
//		assertEquals("", result);
//	}
//
//	@Ignore
//	@Test
//	public void testPostGenerateCodeForConstructor() {
//		// Setup
//		final WorkList           aWl               = new WorkList();
//		final Old_GenerateResult oldGenerateResult = new Old_GenerateResult();
//		oldGenerateResult.outputFiles(val -> {
//		});
//		final Old_GenerateResult oldGenerateResult1 = new Old_GenerateResult();
//		oldGenerateResult1.outputFiles(val -> {
//		});
//		final GenerateResultEnv aFileGen = new GenerateResultEnv(null, null, new WorkManager(), new WorkList(),
//																 new GM_GenerateModule(new GM_GenerateModuleRequest(
//																		 new GN_GenerateNodesIntoSink(
//																				 new GN_GenerateNodesIntoSinkEnv(
//																						 List.of(), null,
//																						 new EIT_ModuleList(List.of()),
//																						 ElLog.Verbosity.SILENT,
//																						 oldGenerateResult, null,
//																						 new CompilationEnclosure(
//																								 null))), null,
//																		 new GN_GenerateNodesIntoSinkEnv(List.of(),
//																										 null,
//																										 new EIT_ModuleList(
//																												 List.of()),
//																										 ElLog.Verbosity.SILENT,
//																										 oldGenerateResult1,
//																										 null,
//																										 new CompilationEnclosure(
//																												 null)))));
//
//		// Run the test
//		whyNotGarishConstructorUnderTest.postGenerateCodeForConstructor(aWl, aFileGen);
//
//		// Verify the results
//		// Confirm GenerateC.generate_class(...).
//		final Old_GenerateResult oldGenerateResult2 = new Old_GenerateResult();
//		oldGenerateResult2.outputFiles(val -> {
//		});
//		final Old_GenerateResult oldGenerateResult3 = new Old_GenerateResult();
//		oldGenerateResult3.outputFiles(val -> {
//		});
//		final GenerateResultEnv aFileGen1 = new GenerateResultEnv(null, null, new WorkManager(), new WorkList(),
//																  new GM_GenerateModule(new GM_GenerateModuleRequest(
//																		  new GN_GenerateNodesIntoSink(
//																				  new GN_GenerateNodesIntoSinkEnv(
//																						  List.of(), null,
//																						  new EIT_ModuleList(List.of()),
//																						  ElLog.Verbosity.SILENT,
//																						  oldGenerateResult2, null,
//																						  new CompilationEnclosure(
//																								  null))), null,
//																		  new GN_GenerateNodesIntoSinkEnv(List.of(),
//																										  null,
//																										  new EIT_ModuleList(
//																												  List.of()),
//																										  ElLog.Verbosity.SILENT,
//																										  oldGenerateResult3,
//																										  null,
//																										  new CompilationEnclosure(
//																												  null)))));
//		verify(mockAGenerateC).generate_class(eq(aFileGen1), any(EvaClass.class));
//	}
//}

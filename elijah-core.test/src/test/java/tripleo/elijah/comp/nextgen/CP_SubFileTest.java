package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import tripleo.elijah_durable_congenial.comp.IO;
import tripleo.elijah_durable_congenial.comp.StdErrSink;
import tripleo.elijah_durable_congenial.comp.internal.CompilationImpl;
import tripleo.elijah_durable_congenial.comp.nextgen.CP_OutputPath;
import tripleo.elijah_durable_congenial.comp.nextgen.CP_Path;
import tripleo.elijah_durable_congenial.comp.signal.DeducePipeline_finishedSignal;
import tripleo.elijah_durable_congenial.factory.comp.CompilationFactory;

import static org.junit.Assert.assertEquals;

public class CP_SubFileTest {

	private CP_OutputPath op;

	@Before
	public void setUp() throws Exception {
		final @NotNull CompilationImpl cc = CompilationFactory.mkCompilation(new StdErrSink(), new IO());
		op = new CP_OutputPath(cc);

		assertEquals(true, cc.getSignalResult(DeducePipeline_finishedSignal.INSTANCE));
	}

	@Test
	public void one() {
		op.testShim();
		op.signalCalculateFinishParse();

		var sf = op.child("foo");
		assertEquals("COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo", "" + sf.getPath());

		final CP_Path sf1 = op.child("foo").child("bar");
		assertEquals("COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo/bar", "" + sf1.getPath());

		final CP_Path sf2 = op.child("foo").child("bar").child("cat");
		assertEquals("COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo/bar/cat", "" + sf2.getPath());
	}
}

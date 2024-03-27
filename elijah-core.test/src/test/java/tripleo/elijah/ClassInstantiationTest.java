package tripleo.elijah;

import org.junit.Test;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.signal.DeducePipeline_finishedSignal;
import tripleo.elijah_durable_congenial.factory.comp.CompilationFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created 3/5/21 4:32 AM
 */
public class ClassInstantiationTest {

	@Test
	public void classInstantiation() {
		final String      f = "test/basic1/class_instantiation/";
		final Compilation  c    = CompilationFactory.mkCompilationSilent();

		c.feedSingleFile(f);

		System.err.println("Errorcount is " + c.errorCount());

		assertThat(c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE), equalTo(1_000_000));
		assertThat(c.errorCount(), equalTo(1_000_000));
	}

	@Test
	public void classInstantiation2() {
		final String      f = "test/basic1/class_instantiation2/";
		final Compilation  c    = CompilationFactory.mkCompilationSilent();

		c.feedSingleFile(f);

		System.err.println("Errorcount is " + c.errorCount());

		assertThat(c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE), equalTo(1_000_000));
		assertThat(c.errorCount(), equalTo(1_000_000));
	}

	@Test
	public void classInstantiation3() {
		final String      f = "test/basic1/class_instantiation3/";
		final Compilation  c    = CompilationFactory.mkCompilationSilent();

		c.feedSingleFile(f);

		System.err.println("Errorcount is " + c.errorCount());

		assertThat(c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE), equalTo(1_000_000));
		assertThat(c.errorCount(), equalTo(1_000_000));
	}

	@Test
	public void classInstantiation4() {
		final String      f = "test/basic1/class_instantiation4/";
		final Compilation  c    = CompilationFactory.mkCompilationSilent();

		c.feedSingleFile(f);

		System.err.println("Errorcount is " + c.errorCount());

		assertThat(c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE), equalTo(1_000_000));
		assertThat(c.errorCount(), equalTo(1_000_000));
	}
}

package tripleo.elijah_durable_congenial.factory.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.IO;
import tripleo.elijah_durable_congenial.comp.StdErrSink;
import tripleo.elijah_durable_congenial.comp.i.ErrSink;
import tripleo.elijah_durable_congenial.comp.internal.CompilationImpl;
import tripleo.elijah_durable_congenial.stages.deduce.IFunctionMapHook;
import tripleo.elijah_durable_congenial.comp.IO;
import tripleo.elijah_durable_congenial.comp.StdErrSink;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.ErrSink;
import tripleo.elijah_durable_congenial.comp.internal.CompilationImpl;

import java.util.List;

public enum CompilationFactory {
	;

	public static @NotNull CompilationImpl mkCompilation2(final List<IFunctionMapHook> aMapHooks) {
		final StdErrSink errSink = new StdErrSink();
		final IO         io      = new IO();

		final @NotNull CompilationImpl c = mkCompilation(errSink, io);
		var REPORTS = c.reports();
		errSink.setRep(REPORTS);

		c.testMapHooks(aMapHooks);

		return c;
	}

	@Contract("_, _ -> new")
	public static @NotNull CompilationImpl mkCompilation(final ErrSink aErrSink, final IO io) {
		final CompilationImpl c = new CompilationImpl(aErrSink, io);
		var REPORTS = c.reports();
		if (aErrSink instanceof StdErrSink stdErrSink) {
			stdErrSink.setRep(REPORTS);
		}
		return c;
	}

	public static @NotNull Compilation mkCompilationSilent(final StdErrSink aStdErrSink, final IO aIO) {
		final Compilation c = mkCompilation(aStdErrSink, aIO);
		var REPORTS = c.reports();
		aStdErrSink.setRep(REPORTS);
		REPORTS.turnAllOutputOff();
		return c;
	}

	public static Compilation mkCompilationSilent() {
		return mkCompilationSilent(new StdErrSink(), new IO());
	}
}

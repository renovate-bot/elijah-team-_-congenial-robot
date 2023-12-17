package tripleo.elijah.factory.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.stages.deduce.IFunctionMapHook;

import java.util.List;

public enum CompilationFactory {
	;

	@Contract("_, _ -> new")
	public static @NotNull CompilationImpl mkCompilation(final ErrSink aErrSink, final IO io) {
		final CompilationImpl c = new CompilationImpl(aErrSink, io);
		if (aErrSink instanceof StdErrSink stdErrSink) {
			stdErrSink.setRep(c.reports());
		}
		return c;
	}

	public static @NotNull CompilationImpl mkCompilation2(final List<IFunctionMapHook> aMapHooks) {
		final StdErrSink errSink = new StdErrSink();
		final IO         io      = new IO();

		final @NotNull CompilationImpl c = mkCompilation(errSink, io);
		errSink.setRep(c.reports());

		c.testMapHooks(aMapHooks);

		return c;
	}

	public static @NotNull Compilation mkCompilationSilent(final StdErrSink aStdErrSink, final IO aIO) {
		final Compilation c = mkCompilation(aStdErrSink, aIO);
		aStdErrSink.setRep(c.reports());
		c.reports().turnAllOutputOff();
		return c;
	}
}

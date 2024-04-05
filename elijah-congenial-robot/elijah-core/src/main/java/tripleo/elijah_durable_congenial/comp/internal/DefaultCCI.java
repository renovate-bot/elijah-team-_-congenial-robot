package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Maybe;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.i.*;

public class DefaultCCI implements CCI {
	//private final @NotNull Compilation compilation;
	private final CIS           _cis;
	private final IProgressSink _ps;

	@Contract(pure = true)
	public DefaultCCI(final @NotNull Compilation aCompilation, final CIS aCis, final IProgressSink aProgressSink) {
		//compilation = aCompilation;
		_cis = aCis;
		_ps  = aProgressSink;
	}

	@Override
	public void accept(final @NotNull Maybe<ILazyCompilerInstructions> mcci, final @NotNull IProgressSink aPs) {
		if (mcci.isException()) return;

		final ILazyCompilerInstructions cci = mcci.o;
		final CompilerInstructions      ci  = cci.get();

		aPs.note(IProgressSink.Codes.DefaultCCI_accept, ProgressSinkComponent.CCI, -1, new Object[]{ci.getName()});

		IProgressSink t = null;
		try {
			t       = _cis.ps();
			_cis.set_ps(aPs);
			_cis.onNext(ci); //CIO::l.add(aCompilerInstructions);
		} finally {
			_cis.set_ps(t);
		}
		//compilation.pushItem(ci);
	}
}

package tripleo.elijah_durable_congenial.comp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.i.Compilation;

import java.util.ArrayList;
import java.util.List;

public class CompilerInstructionsObserver implements Observer<CompilerInstructions> {
	private final Compilation                compilation;
	private final List<CompilerInstructions> l = new ArrayList<>();

	public CompilerInstructionsObserver(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	public void almostComplete() {
		compilation.hasInstructions(l);
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		//Disposable x = d;
		//assert ((ReplaySubject.ReplayDisposable)x.downstream == this;
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		NotImplementedException.raise();
	}

	@Override
	public void onNext(@NonNull final CompilerInstructions aCompilerInstructions) {
		l.add(aCompilerInstructions);
	}

	@Override
	public void onComplete() {
		throw new IllegalStateException("Error");
	}
}

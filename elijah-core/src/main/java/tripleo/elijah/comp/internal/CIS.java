package tripleo.elijah.comp.internal;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.CompilerInstructionsObserver;
import tripleo.elijah.comp.i.IProgressSink;

public class CIS implements Observer<CompilerInstructions> {

	public        CompilerInstructionsObserver  _cio;
	private final Subject<CompilerInstructions> compilerInstructionsSubject = ReplaySubject.<CompilerInstructions>create();
	public        IProgressSink                 ps;

	public void almostComplete() {
		_cio.almostComplete();
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		compilerInstructionsSubject.onSubscribe(d);
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		compilerInstructionsSubject.onError(e);
	}

	@Override
	public void onNext(@NonNull final CompilerInstructions aCompilerInstructions) {
		compilerInstructionsSubject.onNext(aCompilerInstructions);
	}

	@Override
	public void onComplete() {
		throw new IllegalStateException();
		//compilerInstructionsSubject.onComplete();
	}

	public void subscribe(final @NotNull Observer<CompilerInstructions> aCio) {
		compilerInstructionsSubject.subscribe(aCio);
	}
}

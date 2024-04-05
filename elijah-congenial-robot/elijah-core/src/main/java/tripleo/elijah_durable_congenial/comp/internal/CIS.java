package tripleo.elijah_durable_congenial.comp.internal;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.ProgramIsLikelyWrong;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.comp.CompilerInstructionsObserver;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.IProgressSink;

public class CIS implements Observer<CompilerInstructions> {
	private final Subject<CompilerInstructions> compilerInstructionsSubject = ReplaySubject.<CompilerInstructions>create();
	@SuppressWarnings("unused")
	private       IProgressSink                ps;
	private final Compilation c;

	public CIS(final Compilation aC) {
		this.c = aC;
	}

	public void almostComplete() {
		this.c.get_cpp(CPx.cis).then(new DoneCallback<Object>() {
			@Override
			public void onDone(final Object result) {
				CompilerInstructionsObserver cio = (CompilerInstructionsObserver) result;
				cio.almostComplete();
			}
		});
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		compilerInstructionsSubject.onSubscribe(d);
	}

	@Override
	public void onNext(@NonNull final CompilerInstructions aCompilerInstructions) {
		compilerInstructionsSubject.onNext(aCompilerInstructions);
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		compilerInstructionsSubject.onError(e);
	}

	@Override
	public void onComplete() {
		throw new ProgramIsLikelyWrong();
	}

	public void subscribe(final @NotNull Observer<CompilerInstructions> aCio) {
		compilerInstructionsSubject.subscribe(aCio);
	}

	public void set_ps(final IProgressSink aT) {
		ps = aT;
	}

	public IProgressSink ps() {
		return ps;
	}
}

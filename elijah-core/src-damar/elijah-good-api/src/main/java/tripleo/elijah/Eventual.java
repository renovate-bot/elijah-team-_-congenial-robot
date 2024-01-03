package tripleo.elijah;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.diagnostic.Diagnostic;

import java.util.Optional;
import java.util.function.Supplier;

public class Eventual<P> {
	private final DeferredObject<P, Diagnostic, Void> prom = new DeferredObject<>();

	public void resolve(final P p) {
		prom.resolve(p);
	}

	public void then(final DoneCallback<? super P> cb) {
		prom.then(cb);
	}

	public void register(final @NotNull EventualRegister ev) {
		ev.register(this);
	}

	public void fail(final Diagnostic d) {
		prom.reject(d);
	}

	public boolean isResolved() {
		return prom.isResolved();
	}

	/**
	 * Please overload this
	 */
	public String description() {
		return "GENERIC-DESCRIPTION";
	}

	public boolean isPending() {
		return prom.isPending();
	}

	public void reject(final Diagnostic aX) {
		System.err.println("8899 [Eventual::reject] "+aX);
	}

	public void onFail(final FailCallback<? super Diagnostic> aO) {
		prom.fail(aO);
	}

	public Optional<P> getOptional() {
		if (!prom.isResolved()) {
			return Optional.empty();
		}
		final @NotNull P[] xx = (P[]) new Object[]{null};
		prom.then(fg -> {
			xx[0] = fg;
		});
		return Optional.of((P) xx[0]);
	}

	public Optional<P> getOptional(Supplier<P> s) {
		if (!prom.isResolved()) {
			return Optional.empty();
		}
		final @NotNull P[] xx = (P[]) new Object[]{null};
		prom.then(fg -> {
			xx[0] = s.get();
		});
		return Optional.of((P) xx[0]);
	}
}

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.Deferred;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.deduce.DeduceTypeResolve;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.NULL_DeduceTypes2;
import tripleo.elijah.stages.deduce.ResolveUnknown;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created 2/4/21 10:11 PM
 */
public abstract class BaseTableEntry {
	private final DeferredObject2<OS_Element, Diagnostic, Void> _p_elementPromise = new DeferredObject2<OS_Element, Diagnostic, Void>() {
		@Override
		public Deferred<OS_Element, Diagnostic, Void> resolve(final @Nullable OS_Element resolve) {
			return __elementPromise_resolve(resolve, (@Nullable OS_Element r) -> {
				if (isResolved()) return this;
				return super.resolve(r);
			}, this);
		}
	};
	private final Eventual<DeduceTypes2>                        _p_DeduceTypes2   = new Eventual<>();
	private final List<StatusListener> statusListenerList = new ArrayList<StatusListener>();
	private       BaseEvaFunction      __gf;
	private       DeduceTypes2         __dt2;
	private       Status               status             = Status.UNCHECKED;
	private       DeduceTypeResolve    typeResolve;

	protected Deferred<OS_Element, Diagnostic, Void> __elementPromise_resolve(final OS_Element resolve,
																			  final Function<@Nullable OS_Element, Deferred<OS_Element, Diagnostic, Void>> c,
																			  final Deferred<OS_Element, Diagnostic, Void> identity) {
		if (resolve == null) {
/*
			NotImplementedException.raise();
			return null; // README 12/24 kinda never reached
			//return identity;
*/
			throw new UnintendedUseException("24j3 ProgramIsLikelyWrong");
		}
		return c.apply(resolve);
	}

	public void _fix_table(final DeduceTypes2 aDeduceTypes2, final @NotNull BaseEvaFunction aEvaFunction) {
		provide(aDeduceTypes2);
		__gf = aEvaFunction;

		//aEvaFunction._informGF((GenerateFunctions gf11)->{});
	}

	public void provide(DeduceTypes2 aDeduceTypes2) {
		__dt2 = aDeduceTypes2;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status aStatus) {
		status = aStatus;
	}

	public void setStatus(Status newStatus, /*@NotNull*/ IElementHolder eh) {
		status = newStatus;
		if (newStatus == Status.KNOWN) {
			if (eh == null || eh.getElement() == null) {
				throw new AssertionError();
			}
		}

		for (int i = 0; i < statusListenerList.size(); i++) {
			final StatusListener statusListener = statusListenerList.get(i);
			statusListener.onChange(eh, newStatus);
		}

		if (newStatus == Status.UNKNOWN) {
			if (_p_elementPromise.isPending()) {
				_p_elementPromise.reject(new ResolveUnknown());
			}
		}
	}

	public void addStatusListener(StatusListener sl) {
		statusListenerList.add(sl);
	}

	public void elementPromise(@Nullable DoneCallback<OS_Element> dc, @Nullable FailCallback<Diagnostic> fc) {
		if (dc != null)
			_p_elementPromise.then(dc);
		if (fc != null)
			_p_elementPromise.fail(fc);
	}

	public @Nullable OS_Element getResolvedElement() {
		if (_p_elementPromise.isResolved()) {
			final OS_Element[] xx = {null};

			_p_elementPromise.then(x -> xx[0] = x);

			return xx[0];
		}

		return null;
	}

	public Eventual<GenType> typeResolvePromise() {
		return typeResolve.typeResolution();
	}

	public void setResolvedElement(OS_Element aResolved_element, GG_ResolveEvent ggre) {
		if (_p_elementPromise.isResolved()) {
			NotImplementedException.raise();
		} else {
			_p_elementPromise.resolve(aResolved_element);
		}
	}

	public void typeResolve(final GenType aGt) {
		typeResolve.typeResolve(aGt);
	}

	public void onDeduceTypes2(final DoneCallback<DeduceTypes2> cb) {
		_p_DeduceTypes2.then(cb);
	}

	public DeferredObject2<OS_Element, Diagnostic, Void> get_p_elementPromise() {
		return _p_elementPromise;
	}

	public BaseEvaFunction get__gf() {
		return __gf;
	}

	public void set__gf(BaseEvaFunction a__gf) {
		__gf = a__gf;
	}

	public DeduceTypes2 get__dt2() {
		return __dt2;
	}

	public void set__dt2(DeduceTypes2 a__dt2) {
		__dt2 = a__dt2;
	}

	public DeduceTypeResolve getTypeResolve() {
		return typeResolve;
	}

	public void setTypeResolve(DeduceTypeResolve aTypeResolve) {
		typeResolve = aTypeResolve;
	}

	protected void setupResolve() {
		typeResolve = new DeduceTypeResolve(this, new NULL_DeduceTypes2());
	}

	public enum Status {
		KNOWN, UNCHECKED, UNKNOWN
	}

	@FunctionalInterface
	public interface StatusListener {
		void onChange(IElementHolder eh, Status newStatus);
	}
}

//
//
//

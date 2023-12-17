/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.Deferred;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jdeferred2.Promise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 2/4/21 10:11 PM
 */
public abstract class BaseTableEntry {
	protected final DeferredObject2<OS_Element, Diagnostic, Void> _p_elementPromise  = new DeferredObject2<OS_Element, Diagnostic, Void>() {
		@Override
		public Deferred<OS_Element, Diagnostic, Void> resolve(final @Nullable OS_Element resolve) {
			if (resolve == null) {
				if (BaseTableEntry.this instanceof VariableTableEntry vte) {
					switch (vte.getVtt()) {
					case SELF, TEMP, RESULT -> {
						return super.resolve(resolve);
					}
					}
				}
				NotImplementedException.raise();
			}
			return super.resolve(resolve);
		}
	};
	private final List<StatusListener> statusListenerList = new ArrayList<StatusListener>();
	protected     DeduceTypes2         __dt2;
	public        BaseEvaFunction      __gf;
	// region status
	protected       Status                                        status             = Status.UNCHECKED;
	DeduceTypeResolve typeResolve;

	// region resolved_element

	public void _fix_table(final DeduceTypes2 aDeduceTypes2, final @NotNull BaseEvaFunction aEvaFunction) {
		__dt2 = aDeduceTypes2;
		__gf  = aEvaFunction;

		//aEvaFunction._informGF((GenerateFunctions gf11)->{});
	}

	public Status getStatus() {
		return status;
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

	// endregion resolved_element

	public Eventual<GenType> typeResolvePromise() {
		return typeResolve.typeResolution();
	}

	public void setResolvedElement(OS_Element aResolved_element) {
		if (_p_elementPromise.isResolved()) {
			NotImplementedException.raise();
		} else
			_p_elementPromise.resolve(aResolved_element);
	}

	public void typeResolve(final GenType aGt) {
		typeResolve.typeResolve(aGt);
	}

	public enum Status {
		KNOWN, UNCHECKED, UNKNOWN
	}

	protected void setupResolve() {
		typeResolve = new DeduceTypeResolve(this, new NULL_DeduceTypes2());
	}

	@FunctionalInterface
	public interface StatusListener {
		void onChange(IElementHolder eh, Status newStatus);
	}
}

//
//
//

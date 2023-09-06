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
import org.jdeferred2.Promise;
import org.jdeferred2.impl.AbstractPromise;
import org.jetbrains.annotations.NotNull;

/**
 * Created 9/2/21 12:09 AM
 */
public class DeferredObject2<D, F, P> extends AbstractPromise<D, F, P> implements Deferred<D, F, P> {
	@Override
	public Deferred<D, F, P> resolve(final D resolve) {
		synchronized (this) {
			if (!isPending())
				throw new IllegalStateException("Deferred object already finished, cannot resolve again");

			this.state         = State.RESOLVED;
			this.resolveResult = resolve;

			try {
				triggerDone(resolve);
			} finally {
				triggerAlways(state, resolve, null);
			}
		}
		return this;
	}

	@Override
	public @NotNull Deferred<D, F, P> reject(final F reject) {
		synchronized (this) {
			if (!isPending())
				throw new IllegalStateException("Deferred object already finished, cannot reject again");
			this.state        = State.REJECTED;
			this.rejectResult = reject;

			try {
				triggerFail(reject);
			} finally {
				triggerAlways(state, null, reject);
			}
		}
		return this;
	}

	@Override
	public @NotNull Deferred<D, F, P> notify(final P progress) {
		synchronized (this) {
			if (!isPending())
				throw new IllegalStateException("Deferred object already finished, cannot notify progress");

			triggerProgress(progress);
		}
		return this;
	}

	public void reset() {
		state         = State.PENDING;
		rejectResult  = null;
		resolveResult = null;
	}

	@Override
	public @NotNull Promise<D, F, P> promise() {
		return this;
	}
}

//
//
//

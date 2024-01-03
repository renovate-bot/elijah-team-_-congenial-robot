/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created 3/17/22 4:29 PM
 */
public class HolderPromise<T> {
	private final List<Consumer<T>> callbacks = new ArrayList<>();
	// TODO do we really even need this field?
	private       T                 el;

	public void onDone(final Consumer<T> callback) {
		callbacks.add(callback);
	}

//	public T get() {
//		return el;
//	}

	public void set(T aHeld) {
		el = aHeld;

		for (Consumer<T> callback : callbacks) {
			callback.accept(el);
		}

		callbacks.clear(); // from jdeferred. maybe have set state?
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

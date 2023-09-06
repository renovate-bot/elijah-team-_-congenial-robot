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

/**
 * Created 3/17/22 4:27 PM
 */
public class Holder<T> {
	private T el;

	public T get() {
		return el;
	}

	public void set(T el) {
		this.el = el;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

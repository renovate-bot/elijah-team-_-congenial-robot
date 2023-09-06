/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.stages.deduce;

import tripleo.elijah.lang.i.OS_Element;

/**
 * Created 12/11/21 9:28 PM
 */
public interface DeduceElement {
	DeclAnchor declAnchor();
	//Promise<GenType, Diagnostic, Void> typePromise();

	OS_Element element();
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

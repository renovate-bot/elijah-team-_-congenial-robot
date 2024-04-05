/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah_durable_congenial.slir;

import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;

/**
 * Created 11/6/21 8:43 AM
 */
public interface SlirElement {
	OS_Element element();

	String name();

	SlirPos partOfSpeech();
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

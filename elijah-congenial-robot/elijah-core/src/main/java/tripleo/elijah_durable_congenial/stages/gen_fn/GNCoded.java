/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah_durable_congenial.stages.gen_fn;

import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;

/**
 * Created 11/19/21 9:08 PM
 */
public interface GNCoded {
	void setCode(int aCode);

	int getCode();

	Role getRole();

	void register(ICodeRegistrar aCr);

	enum Role {
		CLASS, FUNCTION, NAMESPACE
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

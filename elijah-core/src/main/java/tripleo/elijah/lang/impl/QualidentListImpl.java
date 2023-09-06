/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.Qualident;

import java.util.ArrayList;
import java.util.List;

public class QualidentListImpl implements tripleo.elijah.lang.i.QualidentList {

	public @NotNull List<Qualident> parts = new ArrayList<Qualident>();

	@Override
	public void add(final Qualident qid) {
		// TODO Auto-generated method stub
		parts.add(qid);
	}

	@Override
	public List<Qualident> parts() {
		return parts;
	}
}

//
//
//

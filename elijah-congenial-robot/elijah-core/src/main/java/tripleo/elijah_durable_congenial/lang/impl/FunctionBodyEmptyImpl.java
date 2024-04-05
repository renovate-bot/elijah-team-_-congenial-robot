/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import tripleo.elijah_durable_congenial.lang.i.FunctionBody;
import tripleo.elijah_durable_congenial.lang.i.Postcondition;
import tripleo.elijah_durable_congenial.lang.i.Precondition;
import tripleo.elijah_durable_congenial.lang.i.Scope3;
import tripleo.elijah_durable_congenial.lang.i.*;

/**
 * Created 8/23/21 2:37 AM
 */
public class FunctionBodyEmptyImpl implements FunctionBodyEmpty, FunctionBody {
	private boolean _isAbstract;
	private Scope3  sco;

	@Override
	public void addPostCondition(Postcondition aPostcondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPreCondition(Precondition aPrecondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getAbstract() {
		return _isAbstract;
	}

	@Override
	public Scope3 scope3() {
		return this.sco;
	}

	@Override
	public void setAbstract(boolean aAbstract) {

	}

	@Override
	public void setScope3(Scope3 aSc) {
		this.sco = aSc;
	}
}

//
//
//

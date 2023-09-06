/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.Postcondition;
import tripleo.elijah.lang.i.Precondition;
import tripleo.elijah.lang.i.Scope3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 8/23/21 2:36 AM
 */
public class FunctionBodyImpl implements tripleo.elijah.lang.i.FunctionBody {
	private boolean             isAbstract;
	private List<Postcondition> postconditions;

	public  Scope3             scope3;
	private List<Precondition> preconditions;

	@Override
	public void addPostCondition(Postcondition aPostcondition) {
		if (postconditions == null)
			postconditions = new ArrayList<Postcondition>();
		postconditions.add(aPostcondition);
	}

	@Override
	public void addPreCondition(Precondition aPrecondition) {
		if (preconditions == null)
			preconditions = new ArrayList<Precondition>();
		preconditions.add(aPrecondition);
	}

	@Override
	public boolean getAbstract() {
		return isAbstract;
	}

	@Override
	public Scope3 scope3() {
		return scope3;
	}

	@Override
	public void setAbstract(boolean aAbstract) {
		isAbstract = aAbstract;
	}

	@Override
	public void setScope3(Scope3 aSc) {
		scope3 = aSc;
	}
}

//
//
//

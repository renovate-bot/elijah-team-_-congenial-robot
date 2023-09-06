/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.FormalArgList;
import tripleo.elijah.lang.i.FunctionModifiers;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.TypeName;

/**
 * Created 8/23/21 2:32 AM
 */
public class FunctionHeaderImpl implements tripleo.elijah.lang.i.FunctionHeader {
	private FormalArgList     fal;
	private FunctionModifiers mod;
	private IdentExpression   name;
	private TypeName          ret;

	@Override
	public FormalArgList getFal() {
		return fal;
	}

	@Override
	public FunctionModifiers getModifier() {
		return mod;
	}

	@Override
	public IdentExpression getName() {
		return name;
	}

	@Override
	public TypeName getReturnType() {
		return ret;
	}

	@Override
	public void setFal(FormalArgList aFal) {
		fal = aFal;
	}

	@Override
	public void setModifier(FunctionModifiers aModifiers) {
		mod = aModifiers;
	}

	@Override
	public void setName(IdentExpression aIdentExpression) {
		name = aIdentExpression;
	}

	@Override
	public void setReturnType(TypeName aTypeName) {
		ret = aTypeName;
	}
}

//
//
//

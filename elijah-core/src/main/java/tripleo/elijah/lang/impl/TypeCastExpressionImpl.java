/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;

/**
 * @author tripleo
 * <p>
 * Created Apr 16, 2020 at 8:42:54 AM
 */
public class TypeCastExpressionImpl extends AbstractExpression implements tripleo.elijah.lang.i.TypeCastExpression {

	TypeName tn;

	@Override
	public @Nullable OS_Type getType() {
		return null;
	}

	@Override
	public TypeName getTypeName() {
		return tn;
	}

	@Override
	public boolean is_simple() {
		return false;
	}

	@Override
	public void setType(final OS_Type deducedExpression) {

	}

	@Override
	public void setTypeName(final TypeName typeName) {
		tn = typeName;
	}

	@Override
	public TypeName typeName() {
		return tn;
	}
}

//
//
//

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
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Type;

/**
 * Created 5/8/21 7:13 AM
 */
public class UnaryExpressionImpl extends AbstractExpression implements tripleo.elijah.lang.i.UnaryExpression {

	public UnaryExpressionImpl(ExpressionKind aKind, IExpression aExpression) {
		_kind = aKind;
		left  = aExpression;
	}

	@Override
	public @Nullable OS_Type getType() {
		return null;
	}

	@Override
	public boolean is_simple() {
		return false;
	}

	@Override
	public void setType(OS_Type deducedExpression) {

	}

}

//
//
//

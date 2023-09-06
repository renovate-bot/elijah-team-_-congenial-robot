/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 1, 2005 8:28:33 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Type;

public class SubExpressionImpl extends AbstractExpression implements tripleo.elijah.lang.i.SubExpression {

	private final IExpression carrier;

	public SubExpressionImpl(final IExpression ee) {
		carrier = ee;
	}

	@Override
	public IExpression getExpression() {
		return carrier;
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.SUBEXPRESSION;
	}

	@Override
	public OS_Type getType() {
		return carrier.getType();
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public void setType(final OS_Type deducedExpression) {
		throw new IllegalStateException("Cant set this type");
	}
}

//
//
//

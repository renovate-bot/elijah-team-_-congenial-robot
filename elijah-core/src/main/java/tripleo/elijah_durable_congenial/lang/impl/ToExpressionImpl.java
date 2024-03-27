/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import tripleo.elijah_durable_congenial.lang.i.ExpressionKind;
import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.lang.i.ExpressionKind;
import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.lang.i.ToExpression;

/*
 * Created on Apr 18, 2020 at 23:03
 *
 * $Id$
 *
 */

/**
 * really belongs in stages.deduce
 */
public class ToExpressionImpl extends BasicBinaryExpressionImpl implements ToExpression {

	public ToExpressionImpl(final IExpression fromPart, final IExpression toPart) {
		left  = fromPart;
		right = toPart;
		_kind = ExpressionKind.TO_EXPR;
	}
}

//
//
//

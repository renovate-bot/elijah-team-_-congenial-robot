/**
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import tripleo.elijah_durable_congenial.lang.i.ExpressionKind;
import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah_durable_congenial.lang.i.DotExpression;
import tripleo.elijah_durable_congenial.lang.i.ExpressionKind;
import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.lang.i.IdentExpression;

/**
 * @author Tripleo(envy)
 * <p>
 * Created Mar 27, 2020 at 12:59:41 AM
 */
public class DotExpressionImpl extends BasicBinaryExpressionImpl implements DotExpression {

	public DotExpressionImpl(final IExpression ee, final IdentExpression identExpression) {
		left  = ee;
		right = identExpression;
		_kind = ExpressionKind.DOT_EXP;
	}

	public DotExpressionImpl(final IExpression ee, final IExpression aExpression) {
		left  = ee;
		right = aExpression;
		_kind = ExpressionKind.DOT_EXP;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO when is this true or not? see {@link Qualident}
	}

	@Override
	public String toString() {
		return String.format("%s.%s", left, right);
	}

}

//
//
//

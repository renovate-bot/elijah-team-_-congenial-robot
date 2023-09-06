/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 2, 2005 2:28:42 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.IBinaryExpression;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Type;

public class ExpressionBuilder {

	public static @NotNull IExpression build(final IExpression left, final ExpressionKind aType) {
		return new AbstractExpression(left, aType) {
			OS_Type _type;

			@Override
			public OS_Type getType() {
				return _type;
			}

			@Override
			public boolean is_simple() {
				return false; // TODO whoa
			}

			@Override
			public void setType(final OS_Type deducedExpression) {
				_type = deducedExpression;
			}
		};
	}

	public static @NotNull IBinaryExpression build(final IExpression left, final ExpressionKind aType,
												   final IExpression aExpression) {
		return new BasicBinaryExpressionImpl(left, aType, aExpression);
	}

	public static @NotNull IBinaryExpression buildPartial(final IExpression left, final ExpressionKind aType) {
		return new BasicBinaryExpressionImpl(left, aType, null);
	}

}

//
//
//

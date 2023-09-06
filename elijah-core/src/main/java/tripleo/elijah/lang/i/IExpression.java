/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

/*
 * Created on Sep 2, 2005 2:08:03 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.impl.BasicBinaryExpressionImpl;

public interface IExpression {

	IExpression UNASSIGNED = new BasicBinaryExpressionImpl() {
		@Override
		public @NotNull String toString() {
			return "<UNASSIGNED expression>";
		}
	};

	static boolean isConstant(final IExpression expression) {
		return expression instanceof StringExpression || expression instanceof CharLitExpression
				|| expression instanceof FloatExpression || expression instanceof NumericExpression;
	}

	//@NotNull List<FormalArgListItem> getArgs();
	//
	//void setArgs(ExpressionList ael);

	ExpressionKind getKind();

	IExpression getLeft();

	OS_Type getType();

	boolean is_simple();

	String repr_();

//	default boolean is_simple() {
//		switch(getKind()) {
//		case STRING_LITERAL:
//		case CHAR_LITERAL:
//		case NUMERIC:
//		case FLOAT:
//			return true;
//		default:
//			return false;
//		}
//	}

	void setKind(ExpressionKind aIncrement);

	void setLeft(IExpression iexpression);

	void setType(OS_Type deducedExpression);
}

//
//
//

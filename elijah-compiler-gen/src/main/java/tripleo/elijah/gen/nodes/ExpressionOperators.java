/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 * Created Mar 13, 2019 at 10:34:57 AM
 */
package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.NotNull;

/**
 * @author Tripleo(sb)
 *
 */
public enum ExpressionOperators {
	OP_MINUS, OP_MULT;

	@NotNull
	public String getSymbol() {
		final String middle1;
		switch (this) {
		case OP_MINUS:
			middle1 = "-";
			break;
		case OP_MULT:
			middle1 = "*";
			break;
		default:
			throw new IllegalStateException("no such symbol");//NotImplementedException();
		}
		return middle1;
	}


}

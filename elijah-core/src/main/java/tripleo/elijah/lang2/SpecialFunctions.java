/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

/**
 * Created 10/2/20 10:16 AM
 */
public enum SpecialFunctions {
	;

	public static @NotNull String of(final @NotNull ExpressionKind kind) {
		switch (kind) {
		case LT_:
			return "__lt__";
		case GT:
			return "__gt__";
		case GE:
			return "__ge__";
		case INCREMENT:
			return "__preinc__";
		case AUG_MULT:
			return "__imult__";
		case ASSIGNMENT:
			return "__assign__";
		case GET_ITEM:
			return "__getitem__";
		case ADDITION:
			return "__add__";
		case SUBTRACTION:
			return "__sub__";
		case DIVIDE:
			return "__div__";
		case MODULO:
			return "__mod__";
		case MULTIPLY:
			return "__mult__";
		case NOT_EQUAL:
			return "__neq__";
		case EQUAL:
			return "__eq__";
		// unary
		case NEG:
			return "__negate__";
		default:
			throw new IllegalStateException("Unexpected value: " + kind);
		}
	}

	public static @Nullable String reverse_name(final @NotNull String pn) {
		if (pn.equals("__gt__")) // README  explicitly disallow
			return null;
//		if (pn.equals("__eq__"))
//			return "__req__";
		SimplePrintLoggerToRemoveSoon.println_out_2("reverse_name: " + pn);
//		assert false;
		return null;
	}
}

//
//
//

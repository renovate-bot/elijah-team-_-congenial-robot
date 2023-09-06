/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

public enum ExpressionKind {

	ADDITION,

	AS_CAST, ASSIGNMENT, AUG_BAND, AUG_BOR, AUG_BSR, AUG_BXOR, AUG_DIV, AUG_MINUS,

	AUG_MOD,

	AUG_MULT, AUG_PLUS, AUG_SL, // TODO missing AUG_BSL
	AUG_SR, BAND, BNOT,

	BOR, BSHIFTR, BXOR, CAST_TO,

	CHAR_LITERAL, DECREMENT,

	DIVIDE, DOT_EXP,

	EQUAL,

	FLOAT,

	FUNC_EXPR, GE, /* INDEX_OF, */ GET_ITEM,

	GT, IDENT, INCREMENT, IS_A,

	LAND, LE, LNOT, LOR, LSHIFT,

	LT_, MODULO, MULTIPLY, NEG, NOT_EQUAL, NUMERIC,

	POS, POST_DECREMENT, POST_INCREMENT, // TODO missing BSHIFTL

	PROCEDURE_CALL, QIDENT, RSHIFT, SET_ITEM, STRING_LITERAL,

	SUBEXPRESSION, SUBTRACTION, TO_EXPR, VARREF

}

//
//
//

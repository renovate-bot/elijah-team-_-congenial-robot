/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.typinf.lexer;

/**
 * Lexer error exception.
 * <p>
 * pos:
 * Position in the input line where the error occurred.
 */
public class LexerError extends Exception {
	public int pos;

	LexerError(int pos) {
		this.pos = pos;
	}
}

//
//
//

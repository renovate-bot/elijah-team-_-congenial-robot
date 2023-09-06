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
 * A simple Token structure.
 * Contains the token type, value and position.
 */
public class Token {
	public final String type;
	public final String val;
	public final int    pos;

	public Token(String type, String val, int pos) {
		this.type = type;
		this.val  = val;
		this.pos  = pos;
	}

	@Override
	public String toString() {
		return String.format("%s(%s) at %s", this.type, this.val, this.pos);
	}
}

//
//
//

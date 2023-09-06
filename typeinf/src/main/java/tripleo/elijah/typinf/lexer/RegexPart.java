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
 * Created 9/4/21 5:18 PM
 */
public class RegexPart {
	String groupname;
	Regex  regex;

	public RegexPart(String aGroupname, Regex aRegex) {
		groupname = aGroupname;
		regex     = aRegex;
	}
}

//
//
//

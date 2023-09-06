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

/**
 * @author Tripleo
 * <p>
 * Created 	Mar 27, 2020 at 2:08:59 AM
 */
public enum BuiltInTypes {
	Boolean(79),
	String_(8),
	SystemCharacter(9),
	SystemInteger(80),
	Unit(0);

	public static boolean isBooleanText(@NotNull String name) {
		return name.equals("true") || name.equals("false")
				|| name.equals("True") || name.equals("False");
	}

	final int _code;

	BuiltInTypes(final int aCode) {
		_code = aCode;
	}

	public int getCode() {
		return _code;
	}
}

//
//
//

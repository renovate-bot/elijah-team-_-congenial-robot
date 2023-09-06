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
 * Created 10/6/20 3:20 PM
 */
public enum SpecialVariables {
	;

	public static boolean contains(final @NotNull String name) {
		if (name.equals("self")) return true;
		if (name.equals("this")) return true;
		if (name.equals("Value")) return true; // TODO only special sometimes
		return name.equals("Result");
//		tripleo.elijah.util.Stupidity.println_err_2("SpecialVariables: test: "+name);
	}

	public static @NotNull String get(final @NotNull String name) {
		if (name.equals("self")) return "vsc"; // TODO this should be this in Java and C++
		if (name.equals("this")) return "vsc"; // TODO this should be this in Java and C++
		if (name.equals("Value")) return "vsv"; // TODO only special sometimes
		if (name.equals("Result")) return "vsr";
//		tripleo.elijah.util.Stupidity.println_err_2("SpecialVariables: test: "+name);
		return "<Illegal SpecialValue>";
	}
}

//
//
//

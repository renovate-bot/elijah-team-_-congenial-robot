/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.instructions;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created 10/18/20 10:35 PM
 */
public class SymbolIA implements InstructionArgument {
	private final String text;

	public SymbolIA(final String s) {
		this.text = s;
	}

	@Override
	@Contract(pure = true)
	public @NotNull String toString() {
		return "SymbolIA{" +
				"text='" + text + '\'' +
				'}';
	}
}

//
//
//

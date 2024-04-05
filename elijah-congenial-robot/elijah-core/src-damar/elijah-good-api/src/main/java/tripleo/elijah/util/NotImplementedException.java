/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.util;

import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public class NotImplementedException extends RuntimeException {
	public NotImplementedException(final String aAsdasldbhajk) {
		super(aAsdasldbhajk);
	}

	@Contract(pure = true)
	public static void raise() {
		final int y = 2;
		SimplePrintLoggerToRemoveSoon.println_err_2("Should not be here");
	}

	public NotImplementedException() {
		final int y = 2;
		SimplePrintLoggerToRemoveSoon.println_err_2("Should not be here 2");
	}

	public static void raise_stop() {
		final int y = 2;
		SimplePrintLoggerToRemoveSoon.println_err_2("Should not be here");
	}
}

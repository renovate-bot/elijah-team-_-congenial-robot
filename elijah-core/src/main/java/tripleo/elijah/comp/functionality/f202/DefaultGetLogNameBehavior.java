/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.functionality.f202;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.logging.ElLog;

/**
 * Created 8/11/21 5:58 AM
 */
public class DefaultGetLogNameBehavior implements GetLogNameBehavior {
	@Contract(pure = true)
	@Override
	public @NotNull String getLogName(@NotNull ElLog deduceLog) {
		final String s1 = deduceLog.getFileName();
		final String s2 = s1.replace(System.getProperty("file.separator"), "~~");

		return s2 + ".log";
	}
}

//
//
//

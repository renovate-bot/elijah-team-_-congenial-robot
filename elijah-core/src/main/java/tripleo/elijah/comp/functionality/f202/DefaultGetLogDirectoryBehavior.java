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
import tripleo.elijah.comp.i.Compilation;

import java.io.File;

/**
 * Created 8/11/21 5:55 AM
 */
public class DefaultGetLogDirectoryBehavior implements GetLogDirectoryBehavior {
	private final Compilation c;

	@Contract(pure = true)
	public DefaultGetLogDirectoryBehavior(Compilation aCompilation) {
		c = aCompilation;
	}

	@Override
	public @NotNull File getLogDirectory() {
		final File file1 = new File("COMP", c.getCompilationNumberString());
		final File file2 = new File(file1, "logs");


		System.err.println("mkdirs 33 " + file2);
		file2.mkdirs();

		return file2;
	}
}

//
//
//

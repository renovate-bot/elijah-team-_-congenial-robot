/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.Locatable;

import java.io.PrintStream;
import java.util.List;

/**
 * Created 9/9/21 6:25 AM
 */
public class ResolveUnknown implements Diagnostic {
	@Override
	public @NotNull String code() {
		return "E1003";
	}

	private @NotNull String message() {
		return "Can't resolve variable";
	}

	@Override
	public @NotNull Locatable primary() {
		return null;
	}

	@Override
	public void report(@NotNull PrintStream stream) {
		stream.printf("---[%s]---: %s%n", code(), message());
		// linecache.print(primary);
		for (Locatable sec : secondary()) {
			//linecache.print(sec)
		}
		stream.flush();
	}

	@Override
	public @NotNull List<Locatable> secondary() {
		return null;
	}

	@Override
	public @NotNull Severity severity() {
		return Severity.ERROR;
	}
}

//
//
//

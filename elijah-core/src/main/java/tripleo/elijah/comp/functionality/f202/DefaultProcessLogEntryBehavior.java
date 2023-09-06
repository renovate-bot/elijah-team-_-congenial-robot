/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.functionality.f202;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.stages.logging.LogEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created 8/11/21 6:04 AM
 */
public class DefaultProcessLogEntryBehavior implements ProcessLogEntryBehavior {
	private PrintStream ps;
	private String      s1;

	@Override
	public void donePhase() {
		ps.println();
	}

	@Override
	public void finish() {

	}

	@Override
	public void initialize(@NotNull File aLogFile, String aElLogFileName, @NotNull ErrSink aErrSink) {
		try {
			ps = new PrintStream(aLogFile);
			s1 = aElLogFileName;
		} catch (FileNotFoundException exception) {
			aErrSink.exception(exception);
		}
	}

	@Override
	public void processLogEntry(@NotNull LogEntry entry) {
		final String logentry = String.format("[%s] [%tD %tT] %s %s", s1, entry.time, entry.time, entry.level, entry.message);
		ps.println(logentry);
	}

	@Override
	public void processPhase(@NotNull String aPhase) {
		ps.println(aPhase);
		StringBuilder sb = new StringBuilder(aPhase.length());
		for (int i = 0; i < aPhase.length(); i++) {
			sb.append('=');
		}
		ps.println(sb.toString());
	}

	@Override
	public void start() {

	}
}

//
//
//

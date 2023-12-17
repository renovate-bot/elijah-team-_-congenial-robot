/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.functionality.f202;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stages.logging.LogEntry;

import java.io.File;
import java.util.Collection;

/**
 * Created 8/11/21 5:46 AM
 */
public class F202 {
	private final ErrSink errSink;
	GetLogDirectoryBehavior gld;
	GetLogNameBehavior      gln;
	ProcessLogEntryBehavior ple;
	ProgressBehavior        pre;

	public F202(ErrSink aErrSink, Compilation c) {
		errSink = aErrSink;
		gld     = new DefaultGetLogDirectoryBehavior(c);
		gln     = new DefaultGetLogNameBehavior();
		ple     = new DefaultProcessLogEntryBehavior();
		pre     = new DefaultProgressBehavior();
	}

	public void processLogs(@NotNull Collection<ElLog> aElLogs) {
		//if (aElLogs.size() == 0) return; // TODO progress message? should be impossible anyway
		Preconditions.checkArgument(aElLogs.size() > 0);

		ElLog firstLog = aElLogs.iterator().next();

		final String s2    = gln.getLogName(firstLog);
		final File   file2 = gld.getLogDirectory();

		final File   psf = new File(file2, s2);
		final String s1  = firstLog.getFileName();
		pre.reportProgress(psf.toString());

		ple.initialize(psf, s1, errSink);
		ple.start();
		for (ElLog elLog : aElLogs) {
			ple.processPhase(elLog.getPhase());

			for (LogEntry entry : elLog.getEntries()) {
				ple.processLogEntry(entry);
			}

			ple.donePhase();
		}
		ple.finish();
	}
}

//
//
//

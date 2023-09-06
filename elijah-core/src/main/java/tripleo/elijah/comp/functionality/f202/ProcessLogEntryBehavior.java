/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.comp.functionality.f202;

import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.stages.logging.LogEntry;

import java.io.File;

/**
 * Created 8/11/21 5:50 AM
 */
public interface ProcessLogEntryBehavior {
	void donePhase();

	void finish();

	void initialize(File aLogFile, String aElLogFileName, ErrSink aErrSink);

	void processLogEntry(LogEntry aLogEntry);

	void processPhase(String aPhase);

	void start();
}

//
//
//

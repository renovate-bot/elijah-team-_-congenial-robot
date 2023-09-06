/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.logging;

/**
 * Created 8/7/21 5:10 AM
 */
public class LogEntry {
	public long time;

	public Level  level;
	public String message;

	enum Level {
		ERROR, INFO
	}

	public LogEntry(long aTime, Level aLevel, String aS) {
		time    = aTime;
		level   = aLevel;
		message = aS;
	}
}

//
//
//

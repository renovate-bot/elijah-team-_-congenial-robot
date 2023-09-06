/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.diagnostic.Diagnostic;

import java.util.List;

public interface ErrSink {

	int errorCount();

	void info(String format);

	void exception(Exception exception);

	void reportDiagnostic(Diagnostic diagnostic);

	List<Pair<Errors, Object>> list();

	/*@ ensures errorCount() == \old errorCount + 1*/
	void reportError(String s);

	void reportWarning(String s);

	enum Errors {
		ERROR, INFO, WARNING, EXCEPTION, DIAGNOSTIC
	}
}

//
//
//

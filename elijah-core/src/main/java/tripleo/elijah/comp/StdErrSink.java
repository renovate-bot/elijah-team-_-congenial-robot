/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 * Created Mar 25, 2019 at 3:00:39 PM
 */
package tripleo.elijah.comp;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.diagnostic.Diagnostic;

import java.util.List;

/**
 * @author tripleo(sb)
 */
public class StdErrSink implements ErrSink {
	private @Nullable Finally rep;

	private int _errorCount;

	@Override
	public int errorCount() {
		return _errorCount;
	}

	@NotNull
	private List<Pair<Errors, Object>> _list = new java.util.ArrayList<>();

	@Override
	public void info(final String message) {
		_list.add(Pair.of(Errors.INFO, message));
		if(reporting()) {
			System.err.printf("INFO: %s%n", message);
		}
	}

	@Override
	public void exception(final @NotNull Exception e) {
		_errorCount++;
		if (reporting()) {
			System.err.println("exception: " + e);
			e.printStackTrace(System.err);
		}
		_list.add(Pair.of(Errors.EXCEPTION, e));
	}

	private boolean reporting() {
		return rep != null;
	}

	@Override
	public void reportDiagnostic(@NotNull Diagnostic diagnostic) {
		if (diagnostic.severity() == Diagnostic.Severity.ERROR)
			_errorCount++;
		_list.add(Pair.of(Errors.DIAGNOSTIC, diagnostic));
		//08/13 diagnostic.report(System.err);
	}

	@Override
	public List<Pair<Errors, Object>> list() {
		return _list;
	}

	@Override
	public void reportError(final String s) {
		_errorCount++;
		_list.add(Pair.of(Errors.ERROR, s));
		if(reporting()) {
			System.err.printf("ERROR: %s%n", s);
		}
	}

	@Override
	public void reportWarning(final String s) {
		_list.add(Pair.of(Errors.WARNING, s));
		if(reporting()) {
			System.err.printf("WARNING: %s%n", s);
		}
	}

	public void setRep(final Finally aRep) {
		rep = aRep;
	}

	//public List<Pair<Errors, Object>> get_list() {
	//	return _list;
	//}
	//
	//public void set_list(List<Pair<Errors, Object>> a_list) {
	//	_list = a_list;
	//}
}

//
//
//

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
import tripleo.elijah.lang.i.OS_Element;

/**
 * Created 1/12/21 2:10 AM
 */
public abstract class FoundElement {
	private boolean _called;
	private boolean _didntFind;

	public FoundElement(@NotNull DeducePhase aPhase) {
		aPhase.registerFound(this);
	}

	public boolean didntFind() {
		return _didntFind;
	}

	public void doFoundElement(OS_Element e) {
		if (_called) return;

		_didntFind = false;
		_called    = true;
		foundElement(e);
	}

	public void doNoFoundElement() {
		if (_called) return;

		_didntFind = true;
		_called    = true;
		noFoundElement();
	}

	public abstract void foundElement(OS_Element e);

	public abstract void noFoundElement();
}

//
//
//

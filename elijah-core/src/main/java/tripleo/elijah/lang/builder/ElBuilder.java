/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.builder;

import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.OS_Element;

/**
 * Created 12/22/20 11:31 PM
 */
public abstract class ElBuilder {
	protected OS_Element _parent;

	protected abstract OS_Element build();

	protected abstract void setContext(Context context);

	public void setParent(OS_Element element) {
		_parent = element;
	}

}

//
//
//

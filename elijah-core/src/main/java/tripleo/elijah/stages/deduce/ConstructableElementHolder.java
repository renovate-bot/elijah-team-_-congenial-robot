/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.gen_fn.Constructable;
import tripleo.elijah.stages.gen_fn.IElementHolder;

/**
 * Created 6/30/21 2:55 AM
 */
public class ConstructableElementHolder implements IElementHolder {
	private final Constructable constructable;
	private final OS_Element    element;

	@Contract(pure = true)
	public ConstructableElementHolder(OS_Element aElement, Constructable aConstructable) {
		element       = aElement;
		constructable = aConstructable;
	}

	public Constructable getConstructable() {
		return constructable;
	}

	@Override
	public OS_Element getElement() {
		return element;
	}
}

//
//
//

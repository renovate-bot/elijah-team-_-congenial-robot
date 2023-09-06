/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Element;

/**
 * Created 6/30/21 2:31 AM
 */
public class GenericElementHolder implements IElementHolder {
	private final @NotNull OS_Element element;

	public GenericElementHolder(final @NotNull OS_Element aElement) {
		element = aElement;
	}

	@Override
	public @NotNull OS_Element getElement() {
		return element;
	}
}

//
//
//

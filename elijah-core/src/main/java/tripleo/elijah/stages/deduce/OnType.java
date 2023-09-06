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
import tripleo.elijah.lang.i.OS_Type;

/**
 * Created 1/24/21 2:06 PM
 */
public interface OnType {
	void noTypeFound();

	void typeDeduced(final @NotNull OS_Type aType);
}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah.g.GModuleItem;

/**
 * Marker interface to represent elements that can be added to a OS_Module
 *
 * @see {@link OS_Module#add(OS_Element) }
 */
public interface ModuleItem extends OS_Element, OS_NamedElement, GModuleItem {
	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

//
//
//

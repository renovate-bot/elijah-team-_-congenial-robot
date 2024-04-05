/* objectWrapperThinlet - http://perso.club-internet.fr/sjobic/thinlet/
 * Copyright (C) 2004 Norbert Barbosa (norbert.barbosa@laposte.net)
 *
 * An extension of the Thinlet GUI toolkit (http://www.thinlet.com)
 * that add wrapper object for thinlet component.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tripleo.vendor.thinlet.objectwrapper;


/**
 * A OWObject define the common API part from the thinlet widget ({@link OWWidget}),
 * and thinlet item/choice/header ({@link OWItem}) that are part of some widget (list, combobox, table)
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public abstract class OWObject {
	protected final OWThinlet fthinlet;
	protected final Object    fcomponent;

	//---------------------------------------------------------------------------------------------
	// API
	//---------------------------------------------------------------------------------------------

	protected OWObject(OWThinlet thinlet, Object component) {
		fthinlet   = thinlet;
		fcomponent = component;
		fthinlet.register(this);
	}

	/**
	 * return the parent object
	 */
	public OWObject getParent() {
		return fthinlet.wrap(fthinlet.getParent(unwrap()));
	}

	/**
	 * return the underline Thinlet component Object
	 */
	public Object unwrap() {
		return fcomponent;
	}

	/**
	 * recursively release this component, and all it's children
	 */
	public void release() {
		fthinlet.unregister(this);
		Object[] children = fthinlet.getItems(unwrap());
		for (int i = 0; i < children.length; i++) {
			OWObject child = fthinlet.wrap(children[i]);
			if (child != null)
				fthinlet.unregister(child);
		}
	}


}

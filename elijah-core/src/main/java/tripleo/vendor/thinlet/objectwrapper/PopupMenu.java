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

import org.jetbrains.annotations.NotNull;
import tripleo.vendor.thinlet.Thinlet;
import tripleo.vendor.thinlet.ThinletConstants;

/**
 * Widget that represent the thinlet popupMenu.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class PopupMenu extends OWWidget {

	public PopupMenu(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public PopupMenu(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.POPUPMENU));
	}

	public void defineMenuShown(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.MENUSHOWN, method);
	}

	public void addItem(@NotNull OWObject child) {
		fthinlet.add(unwrap(), child.unwrap());
	}

	public void addItem(@NotNull OWObject child, int index) {
		fthinlet.add(unwrap(), child.unwrap(), index);
	}

	public void removeItem(@NotNull OWObject child) {
		fthinlet.remove(child.unwrap());
	}

	public void removeAllItems() {
		fthinlet.removeAll(unwrap());
	}

	public int getItemCount() {
		return fthinlet.getCount(unwrap());
	}

	public OWObject getItem(int index) {
		return fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public OWObject @NotNull [] getItems() {
		Object[]   o   = fthinlet.getItems(unwrap());
		OWObject[] ret = new OWObject[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = fthinlet.wrap(o[i]);
		return ret;
	}

}

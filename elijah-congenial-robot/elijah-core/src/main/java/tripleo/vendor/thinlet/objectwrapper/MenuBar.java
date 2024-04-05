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
 * Widget that represent the thinlet menubar.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class MenuBar extends OWWidget {

	public MenuBar(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public MenuBar(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.MENUBAR));
	}

	public EnumPlacement getPlacement() {
		return EnumPlacement.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.PLACEMENT));
	}

	public void setPlacement(@NotNull EnumPlacement value) {
		if (value != EnumPlacement.TOP && value != EnumPlacement.BOTTOM)
			throw new IllegalArgumentException("Only TOP or BOTTOM is allowed on menubar: " + value.toString());
		fthinlet.setChoice(unwrap(), ThinletConstants.PLACEMENT, value.toString());
	}

	public void addMenu(@NotNull Menu child) {
		fthinlet.add(unwrap(), child.unwrap());
	}

	public void addMenu(@NotNull Menu child, int index) {
		fthinlet.add(unwrap(), child.unwrap(), index);
	}

	public void removeMenu(@NotNull Menu child) {
		fthinlet.remove(child.unwrap());
	}

	public void removeAllMenu() {
		fthinlet.removeAll(unwrap());
	}

	public int getMenuCount() {
		return fthinlet.getCount(unwrap());
	}

	public Menu getMenu(int index) {
		return (Menu) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public Menu @NotNull [] getMenus() {
		Object[] o   = fthinlet.getItems(unwrap());
		Menu[]   ret = new Menu[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = (Menu) fthinlet.wrap(o[i]);
		return ret;
	}

}

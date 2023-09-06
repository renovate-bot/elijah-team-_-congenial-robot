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

import java.awt.*;

/**
 * Widget that represent the thinlet combobox.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class ComboBox extends TextField {
	public ComboBox(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public ComboBox(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.COMBOBOX));
	}

	public Image getIcon() {
		return fthinlet.getIcon(unwrap(), ThinletConstants.ICON);
	}

	public void setIcon(Image value) {
		fthinlet.setIcon(unwrap(), ThinletConstants.ICON, value);
	}

	public int getSelected() {
		return fthinlet.getSelectedIndex(unwrap());
	}

	public void setSelected(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.SELECTED, value);
	}

	public Choice getSelectedItem() {
		return (Choice) fthinlet.wrap(fthinlet.getSelectedItem(unwrap()));
	}

	public void addItem(@NotNull Choice item) {
		fthinlet.add(unwrap(), item.unwrap());
	}

	public void addItem(@NotNull Choice item, int index) {
		fthinlet.add(unwrap(), item.unwrap(), index);
	}

	public void removeItem(@NotNull Choice item) {
		fthinlet.remove(item.unwrap());
	}

	public void removeAllItem() {
		fthinlet.removeAll(unwrap());
	}

	public int getItemCount() {
		return fthinlet.getCount(unwrap());
	}

	public Choice getItem(int index) {
		return (Choice) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public Choice @NotNull [] getItems() {
		Object[] o   = fthinlet.getItems(unwrap());
		Choice[] ret = new Choice[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = (Choice) fthinlet.wrap(o[i]);
		return ret;
	}


}

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
 * Widget that represent the thinlet tabbedPane.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 * @todo fix the add/remove tab/child
 */
public class TabbedPane extends OWWidget {

	public TabbedPane(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public TabbedPane(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.TABBEDPANE));
	}

	public EnumPlacement getPlacement() {
		return EnumPlacement.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.PLACEMENT));
	}

	public void setPlacement(@NotNull EnumPlacement value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.PLACEMENT, value.toString());
	}

	public boolean isSelected() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.SELECTED);
	}

	public void setSelected(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.SELECTED, value);
	}

	public void defineAction(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.ACTION, method);
	}

	public void add(@NotNull Tab tab, @NotNull OWWidget component) {
		fthinlet.add(unwrap(), tab.unwrap());
		fthinlet.add(unwrap(), component.unwrap());
	}

	public void remove(@NotNull Tab tab) {
		fthinlet.remove(tab.unwrap());
	}

	public void removeAll() {
		fthinlet.removeAll(unwrap());
	}

	public int getChildCount() {
		return fthinlet.getCount(unwrap()) / 2;
	}

	public OWWidget getChild(int index) {
		return (OWWidget) fthinlet.wrap(fthinlet.getItem(unwrap(), index * 2 + 1));
	}

	public OWWidget @NotNull [] getChildren() {
		Object[]   o   = fthinlet.getItems(unwrap());
		OWWidget[] ret = new OWWidget[o.length / 2];
		for (int i = 0; i < ret.length / 2; i++)
			 ret[i] = (OWWidget) fthinlet.wrap(o[i * 2 + 1]);
		return ret;
	}
}

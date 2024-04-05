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
 * Row represent a row on a Table, and contains Cell, for each defined column
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Row extends OWObject {

	public Row(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Row(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.ROW));
	}

	public boolean isSelected() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.SELECTED);
	}

	public void setSelected(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.SELECTED, value);
	}

	public void addCell(@NotNull Cell child) {
		fthinlet.add(unwrap(), child.unwrap());
	}

	public void addCell(@NotNull Cell child, int index) {
		fthinlet.add(unwrap(), child.unwrap(), index);
	}

	public void removeCell(@NotNull Cell child) {
		fthinlet.remove(child.unwrap());
	}

	public void removeAllCell() {
		fthinlet.removeAll(unwrap());
	}

	public int getCellCount() {
		return fthinlet.getCount(unwrap());
	}

	public Cell getCell(int index) {
		return (Cell) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public Cell @NotNull [] getCells() {
		Object[] o   = fthinlet.getItems(unwrap());
		Cell[]   ret = new Cell[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = (Cell) fthinlet.wrap(o[i]);
		return ret;
	}
}

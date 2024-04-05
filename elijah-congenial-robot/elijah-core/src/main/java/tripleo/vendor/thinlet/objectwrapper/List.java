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
import org.jetbrains.annotations.Nullable;
import tripleo.vendor.thinlet.Thinlet;
import tripleo.vendor.thinlet.ThinletConstants;

import java.util.Vector;

/**
 * Widget that represent the thinlet list.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class List extends OWWidget {
	protected static final Item[] EMPTY_ITEMS = {};

	public List(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public List(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.LIST));
	}

	public EnumSelection getSelection() {
		return EnumSelection.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.SELECTION));
	}

	public void setSelection(@NotNull EnumSelection value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.SELECTION, value.toString());
	}

	public boolean isLine() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.LINE);
	}

	public void setLine(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.LINE, value);
	}

	public void defineAction(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.ACTION, method);
	}

	public void definePerform(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.PERFORM, method);
	}

	public void addItem(@NotNull Item item) {
		fthinlet.add(unwrap(), item.unwrap());
	}

	public void addItem(@NotNull Item item, int index) {
		fthinlet.add(unwrap(), item.unwrap(), index);
	}

	public void removeItem(@NotNull Item item) {
		fthinlet.remove(item.unwrap());
	}

	public void removeAllItem() {
		fthinlet.removeAll(unwrap());
	}

	public int getItemCount() {
		return fthinlet.getCount(unwrap());
	}

	public Item getItem(int index) {
		return (Item) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public Item @NotNull [] getItems() {
		Object[] o   = fthinlet.getItems(unwrap());
		Item[]   ret = new Item[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = (Item) fthinlet.wrap(o[i]);
		return ret;
	}

	/**
	 * return the first selected item, or null
	 */
	public @Nullable Item getSelectedItem() {
		Object[] o = fthinlet.getItems(unwrap());
		for (int i = 0; i < o.length; i++) {
			if (fthinlet.getBoolean(o[i], ThinletConstants.SELECTED))
				return (Item) fthinlet.wrap(o[i]);
		}
		return null;
	}

	/**
	 * return the all selected item, or EMPTY_ITEMS
	 */
	public Item[] getSelectedItems() {
		Vector   v = null;
		Object[] o = fthinlet.getItems(unwrap());
		for (int i = 0; i < o.length; i++) {
			if (fthinlet.getBoolean(o[i], ThinletConstants.SELECTED)) {
				if (v == null) v = new Vector();
				v.addElement(fthinlet.wrap(o[i]));
			}
		}
		if (v == null)
			return EMPTY_ITEMS;
		Item[] ret = new Item[v.size()];
		v.toArray(ret);
		return ret;
	}

}

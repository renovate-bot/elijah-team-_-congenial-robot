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
 * Widget that represent the thinlet panel.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Panel extends OWWidget {

	public Panel(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Panel(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.PANEL));
	}

	public int getColumns() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.COLUMNS);
	}

	public void setColumns(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.COLUMNS, value);
	}

	public int getTop() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.TOP);
	}

	public void setTop(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.TOP, value);
	}

	public int getLeft() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.LEFT);
	}

	public void setLeft(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.LEFT, value);
	}

	public int getBottom() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.BOTTOM);
	}

	public void setBottom(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.BOTTOM, value);
	}

	public int getRight() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.RIGHT);
	}

	public void setRight(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.RIGHT, value);
	}

	public int getGap() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.GAP);
	}

	public void setGap(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.GAP, value);
	}

	public String getText() {
		return fthinlet.getString(unwrap(), ThinletConstants.TEXT);
	}

	public void setText(String value) {
		fthinlet.setString(unwrap(), ThinletConstants.TEXT, value);
	}

	public Image getIcon() {
		return fthinlet.getIcon(unwrap(), ThinletConstants.ICON);
	}

	public void setIcon(Image value) {
		fthinlet.setIcon(unwrap(), ThinletConstants.ICON, value);
	}

	public boolean isBorder() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.BORDER);
	}

	public void setBorder(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.BORDER, value);
	}

	public boolean isScrollable() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.SCROLLABLE);
	}

	public void setScrollable(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.SCROLLABLE, value);
	}

	public void addChild(@NotNull OWWidget child) {
		fthinlet.add(unwrap(), child.unwrap());
	}

	public void addChild(@NotNull OWWidget child, int index) {
		fthinlet.add(unwrap(), child.unwrap(), index);
	}

	public void removeChild(@NotNull OWWidget child) {
		fthinlet.remove(child.unwrap());
	}

	public void removeAllChild() {
		fthinlet.removeAll(unwrap());
	}

	public int getChildCount() {
		return fthinlet.getCount(unwrap());
	}

	public OWWidget getChild(int index) {
		return (OWWidget) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public OWWidget @NotNull [] getChildren() {
		Object[]   o   = fthinlet.getItems(unwrap());
		OWWidget[] ret = new OWWidget[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = (OWWidget) fthinlet.wrap(o[i]);
		return ret;
	}
}

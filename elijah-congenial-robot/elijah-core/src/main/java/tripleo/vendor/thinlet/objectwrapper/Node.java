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
 * Node represent a node on a Tree
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Node extends OWItem {
	public Node(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Node(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.NODE));
	}

	public boolean isSelected() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.SELECTED);
	}

	public void setSelected(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.SELECTED, value);
	}

	public boolean isExpanded() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.EXPANDED);
	}

	public void setExpanded(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.EXPANDED, value);
	}

	public @NotNull Node addNode(@NotNull Node item) {
		fthinlet.add(unwrap(), item.unwrap());
		return this;
	}

	public @NotNull Node addNode(@NotNull Node item, int index) {
		fthinlet.add(unwrap(), item.unwrap(), index);
		return this;
	}

	public @NotNull Node removeNode(@NotNull Node item) {
		fthinlet.remove(item.unwrap());
		return this;
	}

	public @NotNull Node removeAllNode() {
		fthinlet.removeAll(unwrap());
		return this;
	}

	public int getNodeCount() {
		return fthinlet.getCount(unwrap());
	}

	public Node getNode(int index) {
		return (Node) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public Node @NotNull [] getNodes() {
		Object[] o   = fthinlet.getItems(unwrap());
		Node[]   ret = new Node[o.length];
		for (int i = 0; i < ret.length; i++)
			 ret[i] = (Node) fthinlet.wrap(o[i]);
		return ret;
	}

}

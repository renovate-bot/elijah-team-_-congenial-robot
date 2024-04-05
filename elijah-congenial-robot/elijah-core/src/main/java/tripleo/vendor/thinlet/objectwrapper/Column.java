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
 * Column represent a column on a Header, for a Table
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Column extends OWItem {
	public Column(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Column(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.COLUMN));
	}

	public int getWidth() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.WIDTH);
	}

	public void setWidth(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.WIDTH, value);
	}

	public EnumSort getSort() {
		return EnumSort.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.SORT));
	}

	public void setSsort(@NotNull EnumSort value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.SORT, value.toString());
	}
}

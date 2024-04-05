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
 * The align Enum
 */
public final class EnumPlacement {
	public static final EnumPlacement TOP     = new EnumPlacement("top");
	public static final EnumPlacement LEFT    = new EnumPlacement("left");
	public static final EnumPlacement BOTTOM  = new EnumPlacement("bottom");
	public static final EnumPlacement RIGHT   = new EnumPlacement("right");
	public static final EnumPlacement STACKED = new EnumPlacement("stacked");

	private static final EnumPlacement[] ALL = {TOP, LEFT, BOTTOM, RIGHT, STACKED};
	private final        String          fvalue;

	private EnumPlacement(String value) {
		fvalue = value;
	}

	public static EnumPlacement fromString(String value) {
		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].fvalue.equals(value))
				return ALL[i];
		}
		throw new IllegalArgumentException(value + " undefined on EnumPlacement");
	}

	public String toString() {
		return fvalue;
	}
}

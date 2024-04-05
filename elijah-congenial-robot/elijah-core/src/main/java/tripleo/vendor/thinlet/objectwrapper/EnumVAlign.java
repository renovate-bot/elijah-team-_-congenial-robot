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
 * The valign Enum
 */
public final class EnumVAlign {
	public static final EnumVAlign FILL   = new EnumVAlign("fill");
	public static final EnumVAlign CENTER = new EnumVAlign("center");
	public static final EnumVAlign TOP    = new EnumVAlign("top");
	public static final EnumVAlign BOTTOM = new EnumVAlign("bottom");


	private static final EnumVAlign[] ALL = {FILL, CENTER, TOP, BOTTOM};
	private final        String       fvalue;

	private EnumVAlign(String value) {
		fvalue = value;
	}

	public static EnumVAlign fromString(String value) {
		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].fvalue.equals(value))
				return ALL[i];
		}
		throw new IllegalArgumentException(value + " undefined on EnumVAlign");
	}

	public String toString() {
		return fvalue;
	}
}

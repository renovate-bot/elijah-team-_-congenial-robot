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
 * Widget that represent the thinlet button.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Button extends Label {

	public Button(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Button(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.BUTTON));
	}

	public EnumType getType() {
		return EnumType.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.TYPE));
	}

	public void setType(@NotNull EnumType value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.TYPE, value.toString());
	}

	public void defineAction(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.ACTION, method);
	}
}

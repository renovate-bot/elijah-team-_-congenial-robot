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
 * Widget that represent the thinlet label.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Label extends OWWidget {

	public Label(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Label(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.LABEL));
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

	public int getMnemonic() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.MNEMONIC);
	}

	public void setMnemonic(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.MNEMONIC, value);
	}

	public EnumAlign getAlignment() {
		return EnumAlign.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.ALIGNMENT));
	}

	public void setAlignment(@NotNull EnumAlign value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.ALIGNMENT, value.toString());
	}

	//    public OWWidget getFor(){
//        return (OWWidget)fthinlet.wrap(fthinlet.getWidget(unwrap(), ThinletConstants.FOR));
//    }
	public void setFor(OWWidget component) {
		fthinlet.setComponent(unwrap(), ThinletConstants.FOR, component);
	}
}

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
 * OWItem represent a complex item used by widget, as the ComboBox choice, list item, menu item, or tree node
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public abstract class OWItem extends OWObject {

	protected OWItem(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public OWItem(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.CHOICE));
	}

	//---------------------------------------------------------------------------------------------
	// specific thinlet API, from dtd
	//---------------------------------------------------------------------------------------------

	public Object getProperty(String key) {
		return fthinlet.getProperty(unwrap(), key);
	}

	public void putProperty(String key, Object value) {
		fthinlet.putProperty(unwrap(), key, value);
	}

	public String getName() {
		return fthinlet.getString(unwrap(), ThinletConstants.NAME);
	}

	public void setName(String v) {
		fthinlet.setString(unwrap(), ThinletConstants.NAME, v);
	}

	public boolean isEnabled() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.ENABLED);
	}

	public void setEnabled(boolean b) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.ENABLED, b);
	}

	public boolean isI18n() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.I18N);
	}

	public void setI18n(boolean b) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.I18N, b);
	}

	public String getTooltip() {
		return fthinlet.getString(unwrap(), ThinletConstants.TOOLTIP);
	}

	public void setTooltip(String v) {
		fthinlet.setString(unwrap(), ThinletConstants.TOOLTIP, v);
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

	public void setIcon(Image icon) {
		fthinlet.setIcon(unwrap(), ThinletConstants.ICON, icon);
	}

	public EnumAlign getAlignment() {
		return EnumAlign.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.ALIGNMENT));
	}

	public void setAlignment(@NotNull EnumAlign value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.ALIGNMENT, value.toString());
	}
}


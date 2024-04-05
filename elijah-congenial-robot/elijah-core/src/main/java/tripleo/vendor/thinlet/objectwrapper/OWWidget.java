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
import tripleo.vendor.thinlet.ThinletConstants;


/**
 * OWWidget represent the base thinlet widget component.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public abstract class OWWidget extends OWObject {


	protected OWWidget(OWThinlet thinlet, Object component) {
		super(thinlet, component);
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

	//---------------------------------------------------------------------------------------------
	// specific thinlet API, from dtd
	//---------------------------------------------------------------------------------------------
	public boolean isVisible() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.VISIBLE);
	}

	public void setVisible(boolean b) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.VISIBLE, b);
	}

	public int getWidth() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.WIDTH);
	}

	public void setWidth(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.WIDTH, value);
	}

	public int getHeight() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.HEIGHT);
	}

	public void setHeight(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.HEIGHT, value);
	}

	public int getColspan() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.COLSPAN);
	}

	public void setColspan(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.COLSPAN, value);
	}

	public int getRowspan() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.ROWSPAN);
	}

	public void setRowspan(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.ROWSPAN, value);
	}

	public int getWeightx() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.WEIGHTX);
	}

	public void setWeightx(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.WEIGHTX, value);
	}

	public int getWeighty() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.WEIGHTY);
	}

	public void setWeighty(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.WEIGHTY, value);
	}

	public EnumHAlign getHalign() {
		return EnumHAlign.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.HALIGN));
	}

	public void setHalign(@NotNull EnumHAlign value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.HALIGN, value.toString());
	}

	public EnumVAlign getValign() {
		return EnumVAlign.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.VALIGN));
	}

	public void setValign(@NotNull EnumVAlign value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.VALIGN, value.toString());
	}

	// methods from ddt definition
	public void defineFocuslost(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.FOCUSLOST, method);
	}

	public void defineFocusgained(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.FOCUSGAINED, method);
	}

	public void setPopupMenu(@NotNull PopupMenu menu) {
		fthinlet.add(unwrap(), menu.unwrap());
	}
}

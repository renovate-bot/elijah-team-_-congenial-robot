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

import tripleo.vendor.thinlet.Thinlet;
import tripleo.vendor.thinlet.ThinletConstants;

/**
 * Widget that represent the thinlet textfield.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class TextField extends OWWidget {

	public TextField(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public TextField(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.TEXTFIELD));
	}

	public String getText() {
		return fthinlet.getString(unwrap(), ThinletConstants.TEXT);
	}

	public void setText(String value) {
		fthinlet.setString(unwrap(), ThinletConstants.TEXT, value);
	}

	public int getColumns() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.COLUMNS);
	}

	public void setColumns(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.COLUMNS, value);
	}

	public boolean isEditable() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.EDITABLE);
	}

	public void setEditable(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.EDITABLE, value);
	}

	public int getStart() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.START);
	}

	public void setStart(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.START, value);
	}

	public int getEnd() {
		return fthinlet.getInteger(unwrap(), ThinletConstants.END);
	}

	public void setEnd(int value) {
		fthinlet.setInteger(unwrap(), ThinletConstants.END, value);
	}

	public void defineAction(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.ACTION, method);
	}

	public void defineInsert(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.INSERT, method);
	}

	public void defineRemove(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.REMOVE, method);
	}

	public void defineCaret(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.CARET, method);
	}

	public void definePerform(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.PERFORM, method);
	}
}

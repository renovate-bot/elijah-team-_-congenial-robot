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
 * Widget that represent the thinlet table.
 *
 * @author Norbert Barbosa
 * @version @owthinlet.version@
 */
public class Table extends OWWidget {
	protected Header fheader;

	public Table(OWThinlet thinlet, Object component) {
		super(thinlet, component);
	}

	public Table(OWThinlet thinlet) {
		super(thinlet, Thinlet.create(ThinletConstants.TABLE));
	}

	public EnumSelection getSelection() {
		return EnumSelection.fromString(fthinlet.getChoice(unwrap(), ThinletConstants.SELECTION));
	}

	public void setSelection(@NotNull EnumSelection value) {
		fthinlet.setChoice(unwrap(), ThinletConstants.SELECTION, value.toString());
	}

	public boolean isLine() {
		return fthinlet.getBoolean(unwrap(), ThinletConstants.LINE);
	}

	public void setLine(boolean value) {
		fthinlet.setBoolean(unwrap(), ThinletConstants.LINE, value);
	}

	public Header getHeader() {
		return fheader;
	}

	public void setHeader(Header header) {
		if (fheader != null)
			fthinlet.remove(fheader.unwrap());
		fheader = header;
		if (fheader != null)
			fthinlet.add(unwrap(), fheader.unwrap(), 0);
	}

	public int gerRowCount() {
		return fthinlet.getCount(unwrap()) - 1;
	}

	public Row getRow(int index) {
		return (Row) fthinlet.wrap(fthinlet.getItem(unwrap(), index));
	}

	public void addRow(@NotNull Row row) {
		fthinlet.add(unwrap(), row.unwrap());
	}

	public void addRow(@NotNull Row row, int index) {
		fthinlet.add(unwrap(), row.unwrap(), index);
	}

	public void removeRow(@NotNull Row row) {
		fthinlet.remove(row.unwrap());
	}

	public void removeAllRows() {
		fthinlet.removeAll(unwrap());
		fthinlet.add(unwrap(), fheader.unwrap(), 0);
	}

	public void defineAction(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.ACTION, method);
	}

	public void definePerform(Method method) {
		fthinlet.defineMethod(fcomponent, ThinletConstants.PERFORM, method);
	}


}

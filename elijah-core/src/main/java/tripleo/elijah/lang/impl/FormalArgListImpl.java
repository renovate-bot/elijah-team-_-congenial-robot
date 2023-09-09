/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.FormalArgListItem;
import tripleo.elijah.lang.i.SmallWriter;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package pak2:
//			FormalArgListItem

public class FormalArgListImpl implements tripleo.elijah.lang.i.FormalArgList {

	public @NotNull List<FormalArgListItem> falis = new ArrayList<FormalArgListItem>();

	@Override
	public List<FormalArgListItem> falis() {
		return falis;
	}

	@Override
	public @NotNull List<FormalArgListItem> items() {
		return falis;
	}

	@Override
	public @NotNull FormalArgListItem next() {
		final FormalArgListItem fali = new FormalArgListItemImpl();
		falis.add(fali);
		return fali;
	}

	@Override
	public String toString() {
		return falis.toString();
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		var i = 1;
		for (FormalArgListItem fali : falis) {
			sw.fieldString("fali%d".formatted(i++), fali.name().asString());
		}

		throw new NotImplementedException();
	}

}

//
//
//

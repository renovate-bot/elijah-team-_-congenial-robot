/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.imports;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.AccessNotation;
import tripleo.elijah_durable_congenial.lang.i.El_Category;
import tripleo.elijah_durable_congenial.lang.i.ImportStatement;
import tripleo.elijah_durable_congenial.lang.impl.AccessNotationImpl;
import tripleo.elijah_durable_congenial.lang.impl.EN_Name_;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Name;
import tripleo.elijah_durable_congenial.lang.i.AccessNotation;
import tripleo.elijah_durable_congenial.lang.i.El_Category;
import tripleo.elijah_durable_congenial.lang.i.ImportStatement;

/**
 * Created 3/26/21 4:55 AM
 */
public abstract class _BaseImportStatement implements ImportStatement {
	// region ClassItem

	protected AccessNotation _access;
	private   AccessNotation access_note;
	private   El_Category    category;

	@Override
	public AccessNotation getAccess() {
		return access_note;
	}

	@Override
	public El_Category getCategory() {
		return category;
	}

	@Override
	public void setAccess(final AccessNotation aNotation) {
		_access = aNotation;
	}

	@Override
	public void setAccess(final AccessNotationImpl aNotation) {
		access_note = aNotation;
	}

	@Override
	public void setCategory(final El_Category aCategory) {
		category = aCategory;
	}

	// endregion

	@Override
	public @NotNull EN_Name getEnName() {
		if (__n == null) {
			__n = EN_Name_.create(name());
		}
		return __n;
	}

	private EN_Name __n;

//	@Override
//	public void serializeTo(final SmallWriter sw) {
//
//	}
}

//
//
//

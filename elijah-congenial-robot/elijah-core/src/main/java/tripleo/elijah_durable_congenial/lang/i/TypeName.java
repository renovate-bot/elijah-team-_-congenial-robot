/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah_congenial_durable.model.source2.SM2_TypeName;

/**
 * Created 8/16/20 2:16 AM
 */
public interface TypeName extends Locatable {
	void setContext(Context context);

	default SM2_TypeName getSourceModel() {return null;}

	enum Nullability {
		NEVER_NULL, NOT_SPECIFIED, NULLABLE
	}

	@Override
	boolean equals(Object o);

	Context getContext();

	boolean isNull();

	Type kindOfType();

	enum Type {
		FUNCTION, GENERIC, NORMAL, TYPE_OF
	}
}

//
//
//

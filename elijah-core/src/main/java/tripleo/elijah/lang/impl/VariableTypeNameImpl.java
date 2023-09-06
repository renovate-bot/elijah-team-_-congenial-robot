/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 1, 2005 4:55:12 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static tripleo.elijah.util.Helpers.List_of;

public class VariableTypeNameImpl extends AbstractTypeName
		implements NormalTypeName, tripleo.elijah.lang.i.VariableTypeName {

	private Context      _ctx;
	// private OS_Type _resolved;
	private OS_Element   _resolvedElement;
	private TypeNameList genericPart;

	@Override
	public void addGenericPart(final TypeNameList tn2) {
		genericPart = tn2;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!super.equals(o))
			return false;
		if (!(o instanceof NormalTypeName))
			return false;
		final NormalTypeName that = (NormalTypeName) o;
		return Objects.equals(genericPart, that.getGenericPart());
	}

	@Override
	public int getColumn() {
		return pr_name.parts().get(0).getColumn();
	}

	@Override
	public int getColumnEnd() {
		return pr_name.parts().get(pr_name.parts().size()).getColumnEnd();
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public File getFile() {
		return pr_name.parts().get(0).getFile();
	}

	@Override
	public int getLine() {
		return pr_name.parts().get(0).getLine();
	}

	@Override
	public int getLineEnd() {
		return pr_name.parts().get(pr_name.parts().size()).getLineEnd();
	}

	@Override
	public TypeNameList getGenericPart() {
		return genericPart;
	}

	@Override
	@NotNull
	public Collection<TypeModifiers> getModifiers() {
		return (tm != null ? List_of(tm) : new ArrayList<TypeModifiers>());
	}

	@Override
	public Qualident getRealName() {
		return pr_name;
	}

	@Override
	public OS_Element getResolvedElement() {
		return _resolvedElement;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), genericPart);
	}

	// region Locatable

	@Override
	public void setContext(final Context ctx) {
		_ctx = ctx;
	}

	@Override
	public boolean hasResolvedElement() {
		return _resolvedElement != null;
	}

	@Override
	public void setResolvedElement(final OS_Element element) {
		_resolvedElement = element;
	}

	@Override
	public @NotNull Type kindOfType() {
		return Type.NORMAL;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	/* #@ requires pr_name != null; */
	// pr_name is null when first created
	@Override
	public String toString() {
		final String result;
		if (pr_name != null) {
			if (genericPart != null) {
				result = String.format("%s[%s]", pr_name.toString(), genericPart.toString());
			} else {
				result = pr_name.toString();
			}
		} else {
			result = "<VariableTypeName null>";
		}
		return result;
	}

	// endregion
}

//
//
//

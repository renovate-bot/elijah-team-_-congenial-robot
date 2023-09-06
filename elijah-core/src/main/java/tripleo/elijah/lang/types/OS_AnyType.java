/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.types;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.BuiltInTypes;

/**
 * Created 4/25/21 4:43 AM
 */
public class OS_AnyType extends __Abstract_OS_Type {
	public OS_AnyType() {
	}

	@Override
	public @NotNull String asString() {
		return "<OS_AnyType>";
	}

	@Override
	public @Nullable BuiltInTypes getBType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable TypeName getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable ClassStatement getClassOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable OS_Type resolve(final Context ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable OS_Element getElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.ANY;
	}

	@Override
	public boolean isUnitType() {
		return false;
	}

	@Override
	public @NotNull Type getType() {
		return Type.ANY;
	}
}

//
//
//

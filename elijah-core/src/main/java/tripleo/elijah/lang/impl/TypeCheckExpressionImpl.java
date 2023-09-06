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
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang2.BuiltInTypes;

/**
 * @author Tripleo
 * <p>
 * Created Apr 18, 2020 at 2:43:00 AM
 */
public class TypeCheckExpressionImpl extends AbstractExpression implements tripleo.elijah.lang.i.TypeCheckExpression {
	private final TypeName    checkfor;
	private final IExpression checking;

	public TypeCheckExpressionImpl(final IExpression ee, final TypeName p1) {
		this.checking = ee;
		this.checkfor = p1;
	}

	@Override
	public @NotNull OS_Type getType() {
		return new OS_BuiltinType(BuiltInTypes.Boolean);
	}

	@Override
	public boolean is_simple() {
		return true; // TODO is not const tho
	}

	@Override
	public void setType(final OS_Type deducedExpression) {
		throw new IllegalStateException("Type of TypeCheckExpression is always boolean");
	}
}

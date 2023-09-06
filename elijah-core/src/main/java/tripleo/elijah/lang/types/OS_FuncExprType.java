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
import tripleo.elijah.lang.i.FuncExpr;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Type;

import java.text.MessageFormat;


/**
 * Created 8/6/20 5:59 PM
 */
public class OS_FuncExprType extends __Abstract_OS_Type {
	private final FuncExpr func_expr;

	public OS_FuncExprType(final FuncExpr funcExpr) {
		this.func_expr = funcExpr;
	}

	@Override
	public @NotNull String asString() {
		return MessageFormat.format("<OS_FuncExprType {0}>", func_expr);
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.FUNC_EXPR && func_expr.equals(aType.getElement());
	}

	@Override
	public OS_Element getElement() {
		return func_expr;
	}

	@Override
	public @NotNull Type getType() {
		return Type.FUNC_EXPR;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("<OS_FuncExprType %s>", func_expr);
	}
}


//
//
//

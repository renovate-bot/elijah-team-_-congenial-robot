/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.impl.*;

/*
 * Created on 5/13/2019 at 01:44
 *
 * $$Id$
 *
 */
public class IntegerNode implements IExpressionNode {
	private final IExpression _expr;
	private final TypeRef     _type;

	public IntegerNode(final NumericExpression aInteger, final TypeRef aTypeRef) {
		_expr = aInteger;
		_type = aTypeRef;
	}

	@Override
	public IExpression getExpr() {
		return _expr;
	}

	@Override
	public boolean is_const_expr() {
		return true;
	}

	@Override
	public boolean is_underscore() {
		return false;
	}

	@Override
	public boolean is_var_ref() {
		return false;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public @NotNull String genText(final CompilerContext cctx) {
		return Integer.toString(((NumericExpression) _expr).getValue());
	}

	@Override
	public @NotNull String genType() {
		return "u64"; // TODO hardcoded
	}

	@Override
	public @NotNull String genText() {
		return Integer.toString(((NumericExpression) _expr).getValue());
	}

	@Override
	public TypeRef getType() {
		return _type;
	}
}

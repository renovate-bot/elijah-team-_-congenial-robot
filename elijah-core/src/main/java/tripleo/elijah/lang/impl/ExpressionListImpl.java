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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Curiously, not an expression
 */
public class ExpressionListImpl implements tripleo.elijah.lang.i.ExpressionList {

	private final List<IExpression> exprs = new ArrayList<IExpression>();

	@Override
	public void add(final IExpression aExpr) {
		exprs.add(aExpr);
	}

	@Override
	public @NotNull Collection<IExpression> expressions() {
		return exprs;
	}

	@Override
	public @NotNull Iterator<IExpression> iterator() {
		return exprs.iterator();
	}

	@Override
	public @NotNull IExpression next(final @NotNull IExpression aExpr) {
//		assert aExpr != null;
		if (aExpr == null)
			throw new IllegalArgumentException("expression cannot be null");
		//
		/* exprs. */
		add(aExpr);
		return aExpr;
	}

	@Override
	public int size() {
		return exprs.size();
	}

	@Override
	public String toString() {
		return exprs.toString();
	}
}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

public class YieldExpressionImpl extends BasicBinaryExpressionImpl
		implements OS_Element, StatementItem, YieldExpression {

	public YieldExpressionImpl(final IExpression aExpr) {
		// TODO Auto-generated constructor stub
		throw new NotImplementedException();
	}

	@Override
	public Context getContext() {
		throw new NotImplementedException();
//		return null;
	}

	@Override
	public OS_Element getParent() {
		throw new NotImplementedException();
//		return null;
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitYield(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}
}

//
//
//

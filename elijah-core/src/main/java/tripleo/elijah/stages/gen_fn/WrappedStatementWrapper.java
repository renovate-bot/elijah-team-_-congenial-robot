/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AbstractExpression;
import tripleo.elijah.lang.impl.StatementWrapperImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang2.ElElementVisitor;

/**
 * Created 9/18/21 4:03 AM
 */
public class WrappedStatementWrapper extends StatementWrapperImpl implements OS_Element {
	private final          VariableStatementImpl vs;
	private final @NotNull Wrapped               wrapped;

	public VariableStatementImpl getVariableStatement() {
		return vs;
	}

	public WrappedStatementWrapper(final IExpression aExpression, final Context aContext, final OS_Element aParent, final VariableStatementImpl aVs) {
		super(aExpression, aContext, aParent);
		vs      = aVs;
		wrapped = new Wrapped(aVs, aExpression);
	}

	@Override
	public @Nullable Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable OS_Element getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public Wrapped getWrapped() {
		return wrapped;
	}

	class Wrapped extends AbstractExpression {

		private final IExpression           expression;
		private final VariableStatementImpl variableStatement;

		public Wrapped(final VariableStatementImpl aVariableStatement, final IExpression aExpression) {
			variableStatement = aVariableStatement;
			expression        = aExpression;
		}

		@Override
		public @Nullable OS_Type getType() {
			return null;
		}

		@Override
		public boolean is_simple() {
			return expression.is_simple();
		}

		@Override
		public void setType(final OS_Type deducedExpression) {

		}
	}

	@Override
	public void visitGen(ElElementVisitor visit) {
		// TODO Auto-generated method stub
//		visit.visitStatementWrapper(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

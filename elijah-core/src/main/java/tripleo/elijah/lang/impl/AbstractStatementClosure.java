/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;

import java.util.ArrayList;
import java.util.List;

public final class AbstractStatementClosure implements StatementClosure, StatementItem {

	private OS_Element               _parent;
	private BlockStatement           bs;
	private ConstructStatement       ctex;
	// public IExpression constructExpression() {
//		ctex=new ConstructStatementImpl(this.parent_scope);
//		add(ctex);
//		return ctex;
//	}
	private IfConditional            ifex;
	final   List<StatementItem>      items = new ArrayList<StatementItem>();
	private Loop                     loop;
	final   Scope                    parent_scope;
	private ProcedureCallExpression  pce;
	private AbstractStatementClosure pcex;
	private VariableSequence         vsq;
	private YieldExpression          yiex;

	public AbstractStatementClosure(final @NotNull ClassStatement classStatement) {
		// TODO check final member
		_parent      = classStatement;
		parent_scope = new AbstractScope2(_parent) {

			@Override
			public void add(final StatementItem aItem) {
				classStatement.addToContainer((ClassItem) aItem);
			}

			@Override
			public void addDocString(final Token s1) {
				classStatement.addDocString(s1);
			}

			@Override
			public @NotNull StatementClosure statementClosure() {
				return AbstractStatementClosure.this; // TODO is this right?
			}

		};
	}

	public AbstractStatementClosure(final Scope aParent) {
		// TODO doesn't set _parent
		parent_scope = aParent;
	}

	public AbstractStatementClosure(final Scope scope, final OS_Element parent1) {
		parent_scope = scope;
		_parent      = parent1;
	}

	@Contract("_ -> param1")
	private StatementItem add(final StatementItem aItem) {
		parent_scope.add(aItem);
		return aItem;
	}

	@Override
	public BlockStatement blockClosure() {
		bs = new BlockStatementImpl(this.parent_scope);
		add(bs);
		return bs;
	}

	@Override
	public tripleo.elijah.lang.i.@NotNull CaseConditional caseConditional(final Context parentContext) {
		final CaseConditional caseConditional = new CaseConditionalImpl(getParent(), parentContext);
		add(caseConditional);
		return caseConditional;
	}

	@Override
	public void constructExpression(final @NotNull IExpression aExpr, final ExpressionList aO) {
		add(new ConstructStatementImpl(_parent, _parent.getContext(), aExpr, null, aO)); // TODO provide for name
	}

	private OS_Element getParent() {
		return _parent;
	}

	@Override
	public IfConditional ifConditional(final OS_Element aParent, final Context cur) {
		ifex = new IfConditionalImpl(aParent, cur);
		add(ifex);
		return ifex;
	}

	@Override
	public Loop loop() {
		loop = new LoopImpl(this.parent_scope.getElement());
		add(loop);
		return loop;
	}

	@Override
	public @NotNull MatchConditional matchConditional(final Context parentContext) {
		final MatchConditional matchConditional = new MatchConditionalImpl(getParent(), parentContext);
		add(matchConditional);
		return matchConditional;
	}

	@Override
	public ProcedureCallExpression procedureCallExpression() {
		pce = new ProcedureCallExpressionImpl();
		add(new StatementWrapperImpl(pce, getParent().getContext(), getParent()));
		return pce;
	}

	@Override
	public void statementWrapper(final IExpression expr) {
		parent_scope.statementWrapper(expr);
	}

	@Override
	public VariableSequence varSeq(final Context ctx) {
		vsq = new VariableSequenceImpl(ctx);
		vsq.setParent(parent_scope.getParent()/* this.getParent() */); // TODO look at this
//		vsq.setContext(ctx); //redundant
		return (VariableSequence) add(vsq);
	}

	@Override
	public void yield(final IExpression aExpr) {
		add(new YieldExpressionImpl(aExpr));
	}
}

//
//
//

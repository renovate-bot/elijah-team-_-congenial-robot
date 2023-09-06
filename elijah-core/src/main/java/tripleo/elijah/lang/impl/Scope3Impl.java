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
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.IfConditionalContext;
import tripleo.elijah.lang.i.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 1/4/21 3:10 AM
 */
public class Scope3Impl implements Scope3, Documentable {
	private final List<Token>            _docstrings = new ArrayList<Token>();
	private final List<OS_Element>       _items      = new ArrayList<OS_Element>();
	private final Scope3StatementClosure asc         = new Scope3StatementClosure();
	private final OS_Element             parent;

	public Scope3Impl(OS_Element aParent) {
		parent = aParent;
	}

	@Override
	public void add(OS_Element element) {
		_items.add(element);
	}

	@Override
	public @NotNull Iterable<? extends Token> docstrings() {
		return _docstrings;
	}

	@Override
	public void addDocString(Token aText) {
		_docstrings.add(aText);
	}

	@Override
	public @NotNull List<OS_Element> items() {
		return _items;
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public @NotNull StatementClosure statementClosure() {
		return asc;
	}

	@Override
	public @NotNull VariableSequence varSeq() {
		return asc.varSeq(asc.getParent().getContext());
	}

	@Override
	public void statementWrapper(IExpression expr) {
		add(new StatementWrapperImpl(expr, parent.getContext(), parent)); // TODO is this right?
	}

	private class Scope3StatementClosure implements StatementClosure {
		@Override
		public @NotNull BlockStatement blockClosure() {
			BlockStatement bs = new BlockStatementImpl(null);
//			add(bs);  // TODO make this an Element
			return bs;
		}

		@Override
		public @NotNull CaseConditional caseConditional(final Context parentContext) {
			final CaseConditional caseConditional = new CaseConditionalImpl(getParent(), parentContext);
			add(caseConditional);
			return caseConditional;
		}

		@Override
		public void constructExpression(final @NotNull IExpression aExpr, final ExpressionList aO) {
			final ConstructStatement constructExpression = new ConstructStatementImpl(parent, parent.getContext(),
																					  aExpr, null, aO); // TODO provide for name
			add(constructExpression);
		}

		private OS_Element getParent() {
			return parent;
		}

		@Override
		public @NotNull IfConditional ifConditional(final OS_Element aParent, final Context cur) {
			IfConditional ifex = new IfConditionalImpl(aParent);
			ifex.setContext(new IfConditionalContext(cur, ifex));
			add(ifex);
			return ifex;
		}

		@Override
		public @NotNull Loop loop() {
			Loop loop = new LoopImpl(parent, parent.getContext());
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
		public @NotNull ProcedureCallExpression procedureCallExpression() {
			ProcedureCallExpression pce = new ProcedureCallExpressionImpl();
			add(new StatementWrapperImpl(pce, getParent().getContext(), getParent()));
			return pce;
		}

		@Override
		public void statementWrapper(final IExpression expr) {
//			parent_scope.statementWrapper(expr);
			add(new StatementWrapperImpl(expr, parent.getContext(), parent)); // TODO is this right?
		}

		@Override
		public @NotNull VariableSequence varSeq(final Context ctx) {
			VariableSequence vsq = new VariableSequenceImpl(ctx);
			vsq.setParent(parent); // TODO look at this
			assert ctx == parent.getContext();
			vsq.setContext(ctx);
			add(vsq);
			return vsq;
		}

		@Override
		public void yield(final IExpression aExpr) {
			final YieldExpression yiex = new YieldExpressionImpl(aExpr);
			add(yiex);
		}
	}


//	private class Scope3StatementClosure implements StatementClosure {
//		@Override
//		public BlockStatement blockClosure() {
//			BlockStatement bs = new BlockStatementImpl(null);
////			add(bs);  // TODO make this an Element
//			return bs;
//		}
//
//		@Override
//		public CaseConditional caseConditional(final Context parentContext) {
//			final CaseConditional caseConditional = new CaseConditionalImpl(getParent(), parentContext);
//			add(caseConditional);
//			return caseConditional;
//		}
//
//		@Override
//		public void constructExpression(final IExpression aExpr, final ExpressionList aO) {
//			final ConstructStatement constructExpression = new ConstructStatementImpl(parent, parent.getContext(), aExpr,
//					null, aO); // TODO provide for name
//			add(constructExpression);
//		}
//
//		private OS_Element getParent() {
//			return parent;
//		}
//
//		@Override
//		public IfConditional ifConditional(final OS_Element aParent, final Context cur) {
//			IfConditional ifex = new IfConditionalImpl(aParent);
//			ifex.setContext(new IfConditionalContext(cur, ifex));
//			add(ifex);
//			return ifex;
//		}
//
//		@Override
//		public Loop loop() {
//			Loop loop = new Loop(parent, parent.getContext());
//			add(loop);
//			return loop;
//		}
//
//		@Override
//		public MatchConditional matchConditional(final Context parentContext) {
//			final MatchConditional matchConditional = new MatchConditional(getParent(), parentContext);
//			add(matchConditional);
//			return matchConditional;
//		}
//
//		@Override
//		public ProcedureCallExpression procedureCallExpression() {
//			ProcedureCallExpression pce = new ProcedureCallExpressionImpl();
//			add(new StatementWrapper(pce, getParent().getContext(), getParent()));
//			return pce;
//		}
//
//		@Override
//		public void statementWrapper(final IExpression expr) {
////			parent_scope.statementWrapper(expr);
//			add(new StatementWrapper(expr, parent.getContext(), parent)); // TODO is this right?
//		}
//
//		@Override
//		public VariableSequence varSeq(final Context ctx) {
//			VariableSequence vsq = new VariableSequence(ctx);
//			vsq.setParent(parent); // TODO look at this
//			assert ctx == parent.getContext();
//			vsq.setContext(ctx);
//			add(vsq);
//			return vsq;
//		}
//
//		@Override
//		public void yield(final IExpression aExpr) {
//			final YieldExpression yiex = new YieldExpression(aExpr);
//			add(yiex);
//		}
//	}
}

//
//
//

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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.CaseContext;
import tripleo.elijah.contexts.SingleIdentContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.util.NotImplementedException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tripleo
 * <p>
 * Created Apr 15, 2020 at 10:09:03 PM
 */
public class CaseConditionalImpl implements tripleo.elijah.lang.i.CaseConditional {

	private final     OS_Element                          parent;
	private @Nullable CaseContext                         __ctx              = null; // TODO look into removing this
	private @Nullable SingleIdentContext                  _ctx               = null;
	private @Nullable CaseConditional                     default_case_scope = null;
	private           IExpression                         expr;
	private @NotNull  HashMap<IExpression, CaseScopeImpl> scopes             = new LinkedHashMap<IExpression, CaseScopeImpl>();

	public CaseConditionalImpl(final OS_Element parent, final Context parentContext) {
		this.parent = parent;
		this._ctx   = new SingleIdentContext(parentContext, this);
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitCaseConditional(this);
	}

	@Override
	public void addScopeFor(IExpression expression, CaseConditional caseScope) {
		// TODO Auto-generated method stub

	}

	//public void addScopeFor(final IExpression expression, final Scope3 caseScope) {
	//	addScopeFor(expression, new CaseScopeImpl(expression, caseScope));
	//}

	@Override
	public void expr(final IExpression expr) {
		this.expr = expr;
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public IExpression getExpr() {
		return expr;
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public HashMap<IExpression, CaseScopeImpl> getScopes() {
		return scopes;
	}

	@Override
	public void postConstruct() {
		// nop
	}

	@Override
	public void scope(Scope3 sco, IExpression expr1) {
		addScopeFor(expr1, new CaseScopeImpl(expr1, sco));
	}

	@Override
	public void setContext(final CaseContext ctx) {
		__ctx = ctx;
	}


	@Override
	public void setDefault() {
		// TODO Auto-generated method stub

	}

	public class CaseScopeImpl implements OS_Container, OS_Element, CaseConditional {

		private final Map<Scope3, IExpression> _scopes    = new HashMap<>();
		private final Scope3                   cscope3;
		private final IExpression              expr1;
		private       boolean                  _isDefault = false;
		private       CaseContext              ctx;

		public CaseScopeImpl(final IExpression expression, Scope3 aScope3) {
			this.expr1   = expression;
			this.cscope3 = aScope3;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#add(tripleo.elijah.lang.i.
		 * OS_Element)
		 */
		@Override
		public void addToContainer(final OS_Element anElement) {
			cscope3.add(anElement);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#addDocString(antlr.Token)
		 */
		@Override
		public void addDocString(final Token s1) {
			cscope3.addDocString(s1);
		}

		@Override
		public void addScopeFor(IExpression expression, CaseConditional caseScope) {
			// TODO Auto-generated method stub

		}

		@Override
		public void expr(IExpression expr) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#getContext()
		 */
		@Override
		public Context getContext() {
			return getParent().getContext();
		}

		@Override
		public @Nullable IExpression getExpr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public @Nullable HashMap<IExpression, CaseScopeImpl> getScopes() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#getParent()
		 */
		@Override
		public @NotNull OS_Element getParent() {
			return CaseConditionalImpl.this;
		}

		@Override
		public void scope(Scope3 aSco, IExpression aExpr1) {
			// TODO Auto-generated method stub
			_scopes.put(aSco, aExpr1);
		}

		@Override
		public void setContext(CaseContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void postConstruct() {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#getItems()
		 */
		public List<OS_Element> getItems() {
			return cscope3.items();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#items()
		 */
		@Override
		public List<OS_NamedElement> items() {
			throw new NotImplementedException();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#setDefault()
		 */
		@Override
		public void setDefault() {
			_isDefault         = true;
			default_case_scope = this;
			_ctx.carrier       = (IdentExpression) expr1;
		}

		@Override
		public void visitGen(final @NotNull ElElementVisitor visit) {
			visit.visitCaseScope(this);
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnsupportedOperationException();
	}
}

//
//
//

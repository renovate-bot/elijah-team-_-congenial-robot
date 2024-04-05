/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah_durable_congenial.contexts.CaseContext;
import tripleo.elijah_durable_congenial.contexts.SingleIdentContext;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tripleo
 * <p>
 * Created Apr 15, 2020 at 10:09:03 PM
 */
public class CaseConditionalImpl implements CaseConditional {

	private final          OS_Element                          parent;
	private final @NotNull HashMap<IExpression, CaseScopeImpl> scopes             = new LinkedHashMap<IExpression, CaseScopeImpl>();
	private @Nullable      CaseContext                         __ctx              = null; // TODO look into removing this
	private @Nullable      SingleIdentContext                  _ctx               = null;
	private                IExpression                         expr;
	private @Nullable      CaseConditional                     default_case_scope = null;

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

	public class CaseScopeImpl implements OS_Container, OS_Element, CaseConditional, CaseScope {
		private final Map<Scope3, IExpression> _scopes    = new HashMap<>();
		private final Scope3                   cscope3;
		private final IExpression              expr1;
		private       boolean                  _isDefault = false;
		private       CaseContext              ctx;

		public CaseScopeImpl(final IExpression expression, Scope3 aScope3) {
			this.expr1   = expression;
			this.cscope3 = aScope3;
		}

		@Override
		public void addToContainer(final OS_Element anElement) {
			cscope3.add(anElement);
		}

		@Override
		public void addDocString(final Token s1) {
			cscope3.addDocString(s1);
		}

		@Override
		public void addScopeFor(IExpression expression, CaseConditional caseScope) {
			throw new UnintendedUseException("24j3 not expected, but");
		}

		@Override
		public void expr(IExpression expr) {
			throw new UnintendedUseException("24j3 not expected, but");
		}

		@Override
		public Context getContext() {
			return getParent().getContext();
		}

		@Override
		public @Nullable IExpression getExpr() {
			throw new UnintendedUseException("24j3 not expected, but");
		}

		@Override
		public @Nullable HashMap<IExpression, CaseScopeImpl> getScopes() {
			throw new UnintendedUseException("24j3 not expected, but");
		}

		@Override
		public @NotNull OS_Element getParent() {
			return CaseConditionalImpl.this;
		}

		@Override
		public void scope(Scope3 aSco, IExpression aExpr1) {
			_scopes.put(aSco, aExpr1);
		}

		@Override
		public void setContext(CaseContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void postConstruct() {
			// REAME 24j3 no-op
		}

		public List<OS_Element> getItems() {
			return cscope3.items();
		}

		@Override
		public List<OS_NamedElement> items() {
			throw new NotImplementedException();
		}

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
			throw new UnintendedUseException("24j3 break for immplementation when reached, but");
		}

		@Override
		public String asString() {
			throw new UnintendedUseException("24j3 not expected, but");
		}
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnintendedUseException("24j3 break for immplementation when reached, but");
	}
}

//
//
//

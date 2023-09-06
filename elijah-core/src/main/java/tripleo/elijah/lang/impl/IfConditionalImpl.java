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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.IfConditionalContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.ArrayList;
import java.util.List;

public class IfConditionalImpl implements tripleo.elijah.lang.i.IfConditional {

	// private final IfConditional sibling;
	private final     List<IfConditional> parts = new ArrayList<IfConditional>();
	// private final List<OS_Element> _items = new ArrayList<OS_Element>();
//	final IfConditionalScope _scope = new IfConditionalScope(this);
	private final     OS_Element          _parent;
	private           IExpression         expr;
	private @Nullable Context             _ctx;
	private           Scope3              scope3;

	public IfConditionalImpl(final @NotNull IfConditional ifExpression) {
//		this.sibling = ifExpression;
		//
		this._ctx    = new IfConditionalContext(ifExpression.getCtx(), this, true);
		this._parent = ifExpression.getParent();
	}

	public IfConditionalImpl(final OS_Element _parent) {
		this._parent = _parent;
		this._ctx    = null;
//		this.sibling = null;
	}

	public IfConditionalImpl(OS_Element aParent, Context aContext) {
		this._parent = aParent;
		setContext(new IfConditionalContext(aContext, this));
	}

	private void add(final StatementItem aItem) {
		scope3.add((OS_Element) aItem);
		// _items.add((OS_Element) aItem);
	}

	@Override
	public @NotNull IfConditional else_() {
		final IfConditional elsepart = new IfConditionalImpl(this);
		parts.add(elsepart);
		return elsepart;
	}

	@Override
	public @NotNull IfConditional elseif() {
		final IfConditional elseifpart = new IfConditionalImpl(this);
		parts.add(elseifpart);
		return elseifpart;
	}

	/**
	 * will not be null during if or elseif
	 *
	 * @param expr
	 */
	@Override
	public void expr(final IExpression expr) {
		this.expr = expr;
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public Context getCtx() {
		return this._ctx;
	}

	@Override
	public IExpression getExpr() {
		return expr;
	}

	@Override
	public List<OS_Element> getItems() {
		return scope3.items();
//		return _items;
	}

	@Override
	public OS_Element getParent() {
		return _parent;
	}

	@Override
	public @NotNull List<IfConditional> getParts() {
		return parts;
	}

	@Override
	public void scope(Scope3 sco) {
		scope3 = sco;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitIfConditional(this);
	}

	/*
	 * private class IfConditionalScopeImpl extends AbstractScope2 { private
	 * List<Token> mDocs;
	 *
	 * protected IfConditionalScope(OS_Element aParent) { super(aParent); assert
	 * aParent == IfConditional.this; }
	 *
	 * @Override public void addDocString(final Token s) { if (mDocs == null) mDocs
	 * = new ArrayList<Token>(); mDocs.add(s); }
	 *
	 * // /*@ requires parent != null; * / // @Override // public void
	 * statementWrapper(final IExpression aExpr) { // //if (parent_scope == null)
	 * throw new IllegalStateException("parent is null"); // add(new
	 * StatementWrapper(aExpr, getContext(), getParent())); // }
	 *
	 * @Override public StatementClosure statementClosure() { return new
	 * AbstractStatementClosure(this); // TODO }
	 *
	 * @Override public void add(final StatementItem aItem) {
	 * IfConditional.this.add(aItem); } }
	 */

	@Override
	public void setContext(final IfConditionalContext ifConditionalContext) {
		_ctx = ifConditionalContext;
	}

	@Override
	public void serializeTo(SmallWriter sw) {
		throw new UnsupportedOperationException();
	}
}

//
//
//

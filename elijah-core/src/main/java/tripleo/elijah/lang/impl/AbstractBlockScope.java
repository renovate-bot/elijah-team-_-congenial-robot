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
import tripleo.elijah.lang.i.*;

/**
 * Created 8/30/20 2:54 PM
 */
public abstract class AbstractBlockScope implements Scope {

	private final          OS_Container             _element;
	private final @NotNull AbstractStatementClosure asc;

	public AbstractBlockScope(final OS_Container _element) {
		this._element = _element;
		this.asc      = new AbstractStatementClosure(this, getParent());
	}

	@Override
	public void add(final StatementItem aItem) {
		if (aItem instanceof FunctionItem)
			_element.add((OS_Element) aItem);
		else
			tripleo.elijah.util.Stupidity
					.println_err_2(String.format("adding false FunctionItem %s", aItem.getClass().getName()));
	}

	@Override
	public void addDocString(final Token aS) {
		_element.addDocString(aS);
	}

	@Override
	public @NotNull BlockStatement blockStatement() {
		return new BlockStatementImpl(this);
	}

	@Override
	public OS_Element getElement() {
		return (OS_Element) _element;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.Scope#getParent()
	 */
	@Override
	public OS_Element getParent() {
		return (OS_Element) _element;
	}

	public abstract Context getContext();

	@Override
	public @Nullable InvariantStatement invariantStatement() {
		return null;
	}

	@Override
	public StatementClosure statementClosure() {
		return asc;
	}

	@Override
	public void statementWrapper(final IExpression aExpr) {
		add(new StatementWrapperImpl(aExpr, getContext(), getParent()));
	}

	@Override
	public @Nullable TypeAliasStatement typeAlias() {
		return null;
	}

}

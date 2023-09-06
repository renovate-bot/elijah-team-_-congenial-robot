/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

import tripleo.elijah.lang.impl.InvariantStatement;

public interface Scope extends Documentable {

	void add(StatementItem aItem);

	BlockStatement blockStatement();

	OS_Element getElement();

	OS_Element getParent();

	InvariantStatement invariantStatement();

	StatementClosure statementClosure();

	void statementWrapper(IExpression aExpr);

	TypeAliasStatement typeAlias();
}

//
//
//

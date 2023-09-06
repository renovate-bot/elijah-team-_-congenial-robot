/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

import antlr.Token;

import java.util.List;

/**
 * Created 1/4/21 3:10 AM
 */
public interface Scope3 extends Documentable {

	public void add(OS_Element element);

	@Override
	public void addDocString(Token aText);

	public Iterable<? extends Token> docstrings();

	public OS_Element getParent();

	public List<OS_Element> items();

	public StatementClosure statementClosure();

	public void statementWrapper(IExpression expr);

	public VariableSequence varSeq();
}

//
//
//

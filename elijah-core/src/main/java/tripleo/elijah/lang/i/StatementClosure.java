/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

public interface StatementClosure {

	BlockStatement blockClosure();

	CaseConditional caseConditional(Context parentContext);

	void constructExpression(IExpression aExpr, ExpressionList aO);

//	StatementClosure procCallExpr();

	IfConditional ifConditional(OS_Element aParent, Context aCtx);

	Loop loop();

	MatchConditional matchConditional(Context parentContext);

	ProcedureCallExpression procedureCallExpression();

	void statementWrapper(IExpression expr);

	VariableSequence varSeq(Context ctx);

	void yield(IExpression aExpr);

	// TODO new
	// IdentList identList();
}

//
//
//

package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.CaseContext;
import tripleo.elijah.lang.impl.CaseConditionalImpl.CaseScopeImpl;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.HashMap;

public interface CaseConditional extends OS_Element, StatementItem, FunctionItem {
	void addScopeFor(IExpression expression, CaseConditional caseScope);

	void expr(IExpression expr);

	@Override
	Context getContext();

	IExpression getExpr();

	@Override
	OS_Element getParent();

	HashMap<IExpression, CaseScopeImpl> getScopes();

	void postConstruct();

	void scope(Scope3 aSco, IExpression aExpr1);

	void setContext(CaseContext ctx);

	void setDefault();

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

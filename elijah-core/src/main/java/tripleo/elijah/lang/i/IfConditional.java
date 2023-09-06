package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.IfConditionalContext;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.List;

public interface IfConditional extends StatementItem, FunctionItem, OS_Element {
	IfConditional else_();

	IfConditional elseif();

	void expr(IExpression expr);

	@Override
	Context getContext();

	Context getCtx();

	IExpression getExpr();

	List<OS_Element> getItems();

	@Override
	OS_Element getParent();

	List<IfConditional> getParts();

	void scope(Scope3 aSco);

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(IfConditionalContext ifConditionalContext);


}

package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.LoopContext;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.List;

public interface Loop extends StatementItem, FunctionItem, OS_Element {
	void expr(IExpression aExpr);

	void frompart(IExpression aExpr);

	@Override
	Context getContext();

	@NotNull
	IExpression getFromPart();

	List<StatementItem> getItems();

	String getIterName();

	IdentExpression getIterNameToken();

	@Override
	OS_Element getParent();

	@NotNull
	IExpression getToPart();

	LoopTypes getType();

	void iterName(IdentExpression s);

	void scope(Scope3 aSco);

	void setContext(LoopContext ctx);

	void topart(IExpression aExpr);

	void type(LoopTypes aType);

	@Override
		// OS_Element
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

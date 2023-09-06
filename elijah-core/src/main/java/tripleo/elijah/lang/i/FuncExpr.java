package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.FuncExprContext;

import java.util.List;

public interface FuncExpr extends IExpression, OS_Element {
	//Collection<FormalArgListItem> getArgs();

	FormalArgList fal();

	List<FormalArgListItem> falis();

	@NotNull List<FormalArgListItem> getArgs();

	@Override
	Context getContext();

	List<FunctionItem> getItems();

	@Override
	ExpressionKind getKind();

	@Override
	OS_Element getParent();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	Scope3 getScope();

	void postConstruct();

	@Nullable TypeName returnType();

	void scope(Scope3 aSco);

	void setArgList(FormalArgList argList);

	void setContext(FuncExprContext ctx);

	void setHeader(FunctionHeader aFunctionHeader);

	void setReturnType(TypeName tn);

	void type(TypeModifiers modifier);
}

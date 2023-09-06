package tripleo.elijah.lang.i;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.lang.impl.IdentExpressionImpl;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.util.Helpers;

public interface IdentExpression extends IExpression, OS_Element, Resolvable, Locatable {
	@Contract("_ -> new")
	static @NotNull IdentExpression forString(String string) {
		return new IdentExpressionImpl(Helpers.makeToken(string), "<inline-absent2>");
	}

	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@NotNull
	String getText();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	void setContext(Context context);

	@Override
	void setKind(ExpressionKind aIncrement);

	@Override
	void setLeft(IExpression iexpression);

	@Override
	void setType(OS_Type deducedExpression);

	EN_Name getName();

	@Override
	void serializeTo(SmallWriter sw);
}

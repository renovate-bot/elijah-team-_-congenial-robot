package tripleo.elijah.lang.i;

import tripleo.elijah.diagnostic.Locatable;

public interface NumericExpression extends IExpression, Locatable {
	@Override
		// IExpression
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@Override
	int getLine();

	@Override
		// IExpression
	OS_Type getType();

	int getValue();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
		// IExpression
	void setKind(ExpressionKind aType);

	@Override
	void setLeft(IExpression aLeft);

	@Override
		// IExpression
	void setType(OS_Type deducedExpression);

	@Override
	String toString();
}

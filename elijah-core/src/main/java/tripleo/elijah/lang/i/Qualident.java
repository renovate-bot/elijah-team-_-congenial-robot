package tripleo.elijah.lang.i;

import antlr.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Qualident extends IExpression {
	static boolean equivalentTokens(@NotNull Token token1, @NotNull Token token2) {
		return token2.getText().equals(token1.getText()) && token2.getLine() == token1.getLine()
				&& token2.getColumn() == token1.getColumn() && token2.getType() == token1.getType();
	}

	void append(IdentExpression r1);

	void appendDot(Token d1);

	@NotNull
	String asSimpleString();

	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@Override
	boolean is_simple();

	List<IdentExpression> parts();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aIncrement);

	@Override
	void setLeft(IExpression iexpression);

	@Override
	void setType(OS_Type deducedExpression);
}

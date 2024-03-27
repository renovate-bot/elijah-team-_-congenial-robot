package tripleo.elijah_durable_congenial.lang.i;

import org.jetbrains.annotations.NotNull;

public interface DotExpression extends IExpression {
	@NotNull
	IExpression getRight();

	@Override
	boolean is_simple();

	@Override
	String toString();
}

package tripleo.elijah.lang.i;

public interface Precondition {
	void expr(IExpression expr);

	void id(IdentExpression id);
}

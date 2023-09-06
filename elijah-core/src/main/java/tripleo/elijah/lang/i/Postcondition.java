package tripleo.elijah.lang.i;

public interface Postcondition {
	void expr(IExpression expr);

	void id(IdentExpression id);
}

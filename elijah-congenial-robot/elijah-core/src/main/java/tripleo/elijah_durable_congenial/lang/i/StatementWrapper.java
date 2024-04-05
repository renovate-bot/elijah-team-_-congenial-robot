package tripleo.elijah_durable_congenial.lang.i;

/*
 * Turns an expression into an element
 */
public interface StatementWrapper extends OS_Element {
	/**
	 * @return the expr
	 */
	IExpression getExpr();

	@Override
	String toString();

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

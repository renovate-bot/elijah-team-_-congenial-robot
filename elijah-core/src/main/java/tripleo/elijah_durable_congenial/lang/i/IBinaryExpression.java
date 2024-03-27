package tripleo.elijah_durable_congenial.lang.i;

public interface IBinaryExpression extends IExpression {

	IExpression getRight();

	void set(IBinaryExpression ex);

	void setRight(IExpression right);

}

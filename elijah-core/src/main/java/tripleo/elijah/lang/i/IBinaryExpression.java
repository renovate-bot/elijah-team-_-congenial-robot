package tripleo.elijah.lang.i;

public interface IBinaryExpression extends IExpression {

	IExpression getRight();

	void set(IBinaryExpression ex);

	void setRight(IExpression right);

}

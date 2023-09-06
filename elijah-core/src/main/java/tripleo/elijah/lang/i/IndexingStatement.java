package tripleo.elijah.lang.i;

import antlr.Token;

public interface IndexingStatement {
	void add(IndexingItem i);

	void setExprs(ExpressionList el);

	void setName(Token i1);
}

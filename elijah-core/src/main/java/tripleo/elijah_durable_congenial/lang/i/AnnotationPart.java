package tripleo.elijah_durable_congenial.lang.i;

public interface AnnotationPart {
	Qualident annoClass();

	ExpressionList getExprs();

	void setClass(Qualident q);

	void setExprs(ExpressionList el);
}

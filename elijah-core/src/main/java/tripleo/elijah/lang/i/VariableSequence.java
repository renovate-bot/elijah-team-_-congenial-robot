package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;

public interface VariableSequence extends FunctionItem, StatementItem, ClassItem {
	void addAnnotation(AnnotationClause a);

	List<AnnotationClause> annotations();

	void defaultModifiers(TypeModifiers aModifiers);

	@Override
	El_Category getCategory();

	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	Collection<VariableStatement> items();

	VariableStatement next();

	@Override
	void setCategory(El_Category aCategory);

	void setContext(Context ctx);

	void setParent(OS_Element parent);

	void setTypeName(TypeName aTn);

	@Override
	String toString();

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

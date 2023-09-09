package tripleo.elijah.lang.i;

import antlr.Token;
import tripleo.elijah.contexts.SyntacticBlockContext;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.List;

public interface SyntacticBlock extends FunctionItem {
	void add(OS_Element anElement);

	void addDocString(Token s1);

	@Override
	Context getContext();

	List<FunctionItem> getItems();

	@Override
	OS_Element getParent();

	List<OS_NamedElement> items();

	void postConstruct();

	void scope(Scope3 sco);

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(SyntacticBlockContext ctx);
}

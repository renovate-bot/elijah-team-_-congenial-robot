package tripleo.elijah_durable_congenial.lang.i;

import antlr.Token;
import tripleo.elijah_durable_congenial.contexts.WithContext;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;

public interface WithStatement extends FunctionItem {
	void add(OS_Element anElement);

	void addDocString(Token aText);

	@Override
	Context getContext();

	List<FunctionItem> getItems();

	@Override
	OS_Element getParent();

	Collection<VariableStatement> getVarItems();

	List<OS_NamedElement> items();

	VariableStatement nextVarStmt();

	void postConstruct();

	void scope(Scope3 sco);

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(WithContext ctx);
}

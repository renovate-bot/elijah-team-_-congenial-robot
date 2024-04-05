package tripleo.elijah_durable_congenial.lang.i;

import tripleo.elijah_durable_congenial.contexts.NamespaceContext;
import tripleo.elijah_durable_congenial.lang.impl.InvariantStatement;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;

import java.util.List;

public interface NamespaceStatement extends ModuleItem, StatementItem, FunctionItem, OS_Container, OS_NamedElement {
	void addAccess(AccessNotation aAcs);

	@Override
		// OS_Container
	void addToContainer(OS_Element anElement);

	void addAnnotations(List<AnnotationClause> aAs);

	List<ClassItem> getItems();

	FunctionDef funcDef();

	@Override
		// OS_Element
	Context getContext();

	NamespaceTypes getKind();

	String getName();

	void setName(IdentExpression aI1);

	OS_Package getPackageName();

	InvariantStatement invariantStatement();

	void postConstruct();

	void setContext(NamespaceContext aCtx);

	void setType(NamespaceTypes aNamespaceTypes);

	StatementClosure statementClosure();

	TypeAliasStatement typeAlias();

	enum Kind {

	}

	@Override
		// OS_Element
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	ProgramClosure XXX();
}

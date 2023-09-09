package tripleo.elijah.lang.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.FunctionContext;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;

public interface FunctionDef extends OS_Element, OS_NamedElement {
	void add(FunctionItem seq);

	FormalArgList fal();

	List<FunctionItem> getItems();

	Collection<FormalArgListItem> getArgs();

	IdentExpression getNameNode();

	Collection<OS_NamedElement> items();

	OS_FuncType getOS_Type();

	@Override
	OS_Element getParent();

	Species getSpecies();

	void scope(Scope3 sco);

	void postConstruct();

	@Nullable
	TypeName returnType();

	void setAnnotations(List<AnnotationClause> aAs);

	void set(FunctionModifiers mod);

	void setAbstract(boolean b);

	void setContext(FunctionContext aContext);

	void setBody(FunctionBody aFunctionBody);

	void setFal(FormalArgList aFal);

	void setName(IdentExpression string_to_ident);

	void setHeader(FunctionHeader aFunctionHeader);

	void setSpecies(Species propGet);

	void setReturnType(TypeName tn);

	@Override
	void visitGen(ElElementVisitor visit); // OS_Element

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	@Override
	String toString();

	enum Species {
		CTOR, DEF_FUN, DTOR, FUNC_EXPR, PROP_GET, PROP_SET, REG_FUN
	}
}

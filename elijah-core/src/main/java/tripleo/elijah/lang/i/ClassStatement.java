package tripleo.elijah.lang.i;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.ClassContext;
import tripleo.elijah.lang.impl.InvariantStatement;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface ClassStatement
		extends ModuleItem, StatementItem, FunctionItem, OS_Element, OS_NamedElement, Documentable, OS_Container {
	List<AnnotationPart> annotationIterable();

	void addAccess(AccessNotation aAcs);

	ConstructorDef addCtor(IdentExpression aX1);

	DestructorDef addDtor();

	DefFunctionDef defFuncDef();

	ClassInheritance classInheritance();

	FunctionDef funcDef();

	Collection<tripleo.elijah.lang.i.ClassItem> findFunction(String string);

	Collection<ConstructorDef> getConstructors();

	@NotNull
	List<TypeName> getGenericPart();

	@Override
		// OS_Element
	ClassContext getContext();

	IdentExpression getNameNode();

	default List<ClassItem> getItems() {
		return items().stream().filter(x -> x instanceof ClassItem).map(x -> (ClassItem) x)
				.collect(Collectors.toList());
	}

	String getName();

	@Override
	OS_Element getParent();

	OS_Type getOS_Type();

	OS_Package getPackageName();

	@Override
	void visitGen(ElElementVisitor visit);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	ClassTypes getType();

	void setType(ClassTypes aClassTypes);

	InvariantStatement invariantStatement();

	void postConstruct();

	PropertyStatement prop();

	void setContext(ClassContext ctx);

	void setHeader(ClassHeader aCh);

	void setName(IdentExpression aCapitalX);

	void setPackageName(OS_Package aPackage1);

	StatementClosure statementClosure();

	void walkAnnotations(AnnotationWalker aWalker);

	public static final class __GetConstructorsHelper {
		public static final Function<ClassItem, ConstructorDef> castClassItemToConstructor = new Function<ClassItem, ConstructorDef>() {
			@Contract(value = "_ -> param1", pure = true)
			@Nullable
			@Override
			public @org.jetbrains.annotations.Nullable ConstructorDef apply(@Nullable ClassItem input) {
				return (ConstructorDef) input;
			}
		};
		public static final Predicate<ClassItem>                selectForConstructors      = new Predicate<ClassItem>() {
			@Contract(value = "null -> false", pure = true)
			@Override
			public boolean apply(@Nullable ClassItem input) {
				return input instanceof ConstructorDef;
			}
		};
	}
}

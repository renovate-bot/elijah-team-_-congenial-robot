/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.ClassContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.impl.ENU_ClassName;
import tripleo.elijah.lang.types.OS_UserClassType;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a "class"
 * <p>
 * items -> ClassItems docstrings variables
 */
public class ClassStatementImpl extends _CommonNC implements ClassItem, tripleo.elijah.lang.i.ClassStatement {

	static final  List<TypeName> emptyTypeNameList = ImmutableList.<TypeName>of();
	private final OS_Element     parent;
	private       OS_Type        __cached_osType;
	private       ClassHeader    hdr;

	public ClassStatementImpl(final @NotNull OS_Element parentElement, final Context parentContext) {
		parent = parentElement; // setParent

		@NotNull final ElObjectType x = DecideElObjectType.getElObjectType(parentElement);
		switch (x) {
		case MODULE:
			final OS_Module module = (OS_Module) parentElement;

			this.setPackageName(module.pullPackageName());
			_packageName.addElement(this);
			module.add(this);
			break;
		case FUNCTION:
			// do nothing
			break;
		default:
			// we kind of fail the switch test here because OS_Container is not an OS_Element,
			// so we have to test explicitly, messing up the pretty flow we had.
			// hey sh*t happens.
			if (parentElement instanceof OS_Container) {
				((OS_Container) parentElement).addToContainer(this);
			} else {
				throw new IllegalStateException(String.format("Cant add ClassStatement to %s", parentElement));
			}
		}

		setContext(new ClassContext(parentContext, this));
	}

	@Override
	public @NotNull List<AnnotationPart> annotationIterable() {
		List<AnnotationClause> annotations = hdr.annos();

		List<AnnotationPart> aps = new ArrayList<AnnotationPart>();
		if (annotations == null)
			return aps;
		for (AnnotationClause annotationClause : annotations) {
			aps.addAll(annotationClause.aps());
		}
		return aps;
	}

	@Override
	public @NotNull ConstructorDef addCtor(final IdentExpression aConstructorName) {
		return new ConstructorDefImpl(aConstructorName, this, getContext());
	}

	@Override
	public @NotNull DestructorDef addDtor() {
		return new DestructorDefImpl(this, getContext());
	}

	@Override
	public @NotNull DefFunctionDef defFuncDef() {
		return new DefFunctionDefImpl(this, getContext());
	}

	@Override
	public ClassInheritance classInheritance() {
		return hdr.inheritancePart();
	}

	@Override
	public @NotNull FunctionDef funcDef() {
		return new FunctionDefImpl(this, getContext());
	}

    @Override
    public @NotNull Collection<ClassItem> findFunction(final String name) {
        return items.stream()
                .filter(item -> {
                    switch (DecideElObjectType.getElObjectType(item)) {
                        case CONSTRUCTOR -> { return false; } // NOTE 10/09 reported redundant, remains retained
                        case FUNCTION -> {
                            return ((FunctionDef) item).name().sameName(name);
                        }
                        default -> { return false; }
                    }})
				.collect(Collectors.toList());
    }

    @Override
    public @NotNull Collection<ConstructorDef> getConstructors() {
        final Collection<ClassItem> x = Collections2.filter(items,
				__GetConstructorsHelper.selectForConstructors);
        final Collection<ConstructorDef> y = Collections2.transform(x,
                __GetConstructorsHelper.castClassItemToConstructor);
        return y;
    }

	@Override
	public @NotNull OS_Type getOS_Type() {
		if (__cached_osType == null)
			__cached_osType = new OS_UserClassType(this);
		return __cached_osType;
	}

	@Override
	public ClassTypes getType() {
		return hdr.type();
	}

	@Override
	public @NotNull List<TypeName> getGenericPart() {
		if (hdr.genericPart() == null)
			return emptyTypeNameList;
		else
			return hdr.genericPart().p();
	}

	@Override
	public void setType(final ClassTypes aType) {
//		_type = aType;
		throw new NotImplementedException();
	}

	@Override
	public IdentExpression getNameNode() {
		return hdr.nameToken();
	}

	// region inheritance

	@Override
	public @org.jetbrains.annotations.Nullable InvariantStatement invariantStatement() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public void postConstruct() {
		assert hdr.nameToken() != null;
		int destructor_count = 0;
		for (ClassItem item : items) {
			if (item instanceof DestructorDef)
				destructor_count++;
		}
		assert destructor_count == 0 || destructor_count == 1;
	}

	@Override
	public @NotNull PropertyStatement prop() {
		PropertyStatement propertyStatement = new PropertyStatementImpl(this, getContext());
		addToContainer(propertyStatement);
		return propertyStatement;
	}

	// endregion

	// region annotations

	@Override // OS_Container
	public void addToContainer(final OS_Element anElement) {
		if (!(anElement instanceof ClassItem))
			throw new IllegalStateException(String.format("Cant add %s to ClassStatement", anElement));
		items.add((ClassItem) anElement);
	}

	// endregion

	// region called from parser

	@Override // OS_Element
	public ClassContext getContext() {
		return (ClassContext) _a.getContext();
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public @NotNull StatementClosure statementClosure() {
		return new AbstractStatementClosure(this);
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.addClass(this); // TODO visitClass
	}

	public void setGenericPart(TypeNameList genericPart) {
//		this.genericPart = genericPart;
		throw new NotImplementedException();
	}

	@Override
	public void setContext(final ClassContext ctx) {
		_a.setContext(ctx);
	}

	@Override
	public void setHeader(ClassHeader aCh) {
		hdr = aCh;

		getEnName().addUnderstanding(new ENU_ClassName());
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		// name inheritance items
		// packagename parent

		// header: annos genericPart inheritancePart (name)
		// type: ABSTRACT, ANNOTATION, EXCEPTION, INTERFACE, NORMAL, SIGNATURE, STRUCTURE

		sw.fieldIdent("name", hdr.nameToken());
		sw.fieldString("type", getType().toString());
		sw.fieldString("packageName", _packageName.getName());
		var pr = sw.createRef(getParent());
		sw.fieldRef("parent", pr);

		var inh = sw.createTypeNameList();
		for (TypeName tn : classInheritance().tns()) {
			inh.add(tn);
		}
		sw.fieldTypenameList("inheritance", inh);

		var genr = sw.createTypeNameList();
		for (TypeName tn : getGenericPart()) {
			genr.add(tn);
		}
		sw.fieldTypenameList("genericPart", inh);

		sw.fieldList("items", getItems());
	}

	@Override
	public @NotNull String getName() {
		if (hdr.nameToken() == null)
			throw new IllegalStateException("null name");
		return hdr.nameToken().getText();
	}

	// endregion

	public void setInheritance(ClassInheritance inh) {
//		_inh = inh;
		throw new NotImplementedException();
	}

	@Override
	public String toString() {
		final String package_name;
		if (getPackageName() != null && getPackageName().getName2() != null) {
			final Qualident package_name_q = getPackageName().getName2();
			package_name = package_name_q.toString();
		} else
			package_name = "`'";
		return String.format("<Class %s %s>", package_name, getName());
	}

	@Override
	public @org.jetbrains.annotations.Nullable EN_Name getEnName() {
		if (hdr == null) return null;

		return hdr.nameToken().getName();
	}

	public @org.jetbrains.annotations.Nullable TypeAliasStatement typeAlias() {
		NotImplementedException.raise();
		return null;
	}

	public @NotNull ProgramClosure XXX() {
		return new ProgramClosureImpl() {
		};
	}

}

//
//
//

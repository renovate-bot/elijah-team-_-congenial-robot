/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.FunctionContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created 6/27/21 6:42 AM
 */
public abstract class BaseFunctionDef implements FunctionDef, Documentable, ClassItem, OS_Container, OS_NamedElement {

	public @NotNull Attached               _a          = new AttachedImpl();
	protected       Species                _species;
	protected       FormalArgList          mFal        = new FormalArgListImpl(); // remove final for FunctionDefBuilder
	protected       Scope3                 scope3;
	@Nullable       List<AnnotationClause> annotations = null;
	private         AccessNotation         access_note;
	private         El_Category            category;
	protected       IdentExpression        funName;

	protected FunctionModifiers mod;
	protected TypeName          rt;

	// region arglist

	@Override
	public void add(final FunctionItem seq) {

	}

	@Override
	public FormalArgList fal() {
		return mFal;
	}

	@Override
	public @NotNull List<FunctionItem> getItems() {
		if (scope3 == null) return Collections.emptyList(); // README ie. interfaces (parent.hdr.type == INTERFACE)


		List<FunctionItem> collection = new ArrayList<FunctionItem>();
		for (OS_Element element : scope3.items()) {
			if (element instanceof FunctionItem)
				collection.add((FunctionItem) element);
		}
		return collection;
		// return mScope2.items;
	}

	@Override
	public IdentExpression getNameNode() {
		return funName;
	}

	// endregion

	@Override // OS_Container
	public @NotNull List<OS_NamedElement> items() {
		final ArrayList<OS_NamedElement> a = new ArrayList<OS_NamedElement>();
		for (final OS_Element functionItem : scope3.items()) {
			if (functionItem instanceof OS_NamedElement)
				a.add((OS_NamedElement) functionItem);
		}
		return a;
	}

	@Override
	public void scope(Scope3 sco) {
		scope3 = sco;
	}

	// region items

	@Override
	public void setBody(final FunctionBody aFunctionBody) {

	}

	@Override
	public @NotNull List<FormalArgListItem> getArgs() {
		return mFal.items();
	}

	@Override
	public void setFal(FormalArgList fal) {
		mFal = fal;
	}

	@Override
	public abstract void setHeader(FunctionHeader aFunctionHeader);

	@Override
	public void setSpecies(final Species aSpecies) {
		_species = aSpecies;
	}

	// endregion

	// region name

	@Override
	public void setReturnType(final TypeName tn) {

	}

	@Override
	public OS_FuncType getOS_Type() {
		return new OS_FuncType(this);
	}

	@Override // OS_Element
	public abstract @Nullable OS_Element getParent();

	// endregion

	// region context

	@Override
	public Species getSpecies() {
		return _species;
	}

	@Override
	public void visitGen(final ElElementVisitor visit) {

	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	// endregion

	// region annotations

	@Override // OS_Container
	public void addToContainer(final OS_Element anElement) {
		if (anElement instanceof FunctionItem) {
//			mScope2.add((StatementItem) anElement);
			scope3.add(anElement);
		} else
			throw new IllegalStateException(String.format("Cant add %s to FunctionDef", anElement));
	}

	public void addAnnotation(final AnnotationClause a) {
		if (annotations == null)
			annotations = new ArrayList<AnnotationClause>();
		annotations.add(a);
	}

	@Override
	public abstract void postConstruct();

	@Override
	public @Nullable TypeName returnType() {
		return rt;
	}

	@Override // Documentable
	public void addDocString(final Token aText) {
		scope3.addDocString(aText);
	}

	@Override
	public void set(final FunctionModifiers mod) {
		this.mod = mod;
	}

	@Override
	public void setAbstract(final boolean b) {

	}

	public @NotNull Iterable<AnnotationPart> annotationIterable() {
		List<AnnotationPart> aps = new ArrayList<AnnotationPart>();
		if (annotations == null)
			return aps;
		for (AnnotationClause annotationClause : annotations) {
			for (AnnotationPart annotationPart : annotationClause.aps()) {
				aps.add(annotationPart);
			}
		}
		return aps;
	}

	// endregion

	// region Documentable

	@Override
	public void setAnnotations(List<AnnotationClause> aAnnotationClauses) {
		annotations = aAnnotationClauses;
	}

	// endregion

	@Override
	public AccessNotation getAccess() {
		return access_note;
	}

	@Override
	public El_Category getCategory() {
		return category;
	}

	// region ClassItem

	public void setContext(final FunctionContext ctx) {
		_a.setContext(ctx);
	}

	@Override
	public void setCategory(El_Category aCategory) {
		category = aCategory;
	}

	@Override
	public void setAccess(AccessNotation aNotation) {
		access_note = aNotation;
	}

	@Override
	public void setName(final IdentExpression aText) {
		funName = aText;
	}

	@Override // OS_Element
	public Context getContext() {
		return _a.getContext();
	}

	public boolean hasItem(OS_Element element) {
		return scope3.items().contains(element);
	}

	@Override // OS_NamedElement
	public OS_ElementName name() {
		if (funName == null) {
            return OS_ElementName_.empty();
        }
		return OS_ElementName_.ofString(funName.getText());
	}

	public void walkAnnotations(@NotNull AnnotationWalker annotationWalker) {
		if (annotations == null)
			return;
		for (AnnotationClause annotationClause : annotations) {
			for (AnnotationPart annotationPart : annotationClause.aps()) {
				annotationWalker.annotation(annotationPart);
			}
		}
	}

	// endregion

	@Override
	public EN_Name getEnName() {
		if (__n == null) {
			__n = EN_Name_.create(name());
		}
		return __n;
	}

	protected EN_Name __n;
}

//
//
//

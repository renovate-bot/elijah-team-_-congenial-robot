/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package pak:
//			TypeRef, IExpression

public class VariableStatementImpl implements OS_Element, tripleo.elijah.lang.i.VariableStatement {

	private final    VariableSequence       _parent;
	@Nullable        List<AnnotationClause> annotations  = null;
	private          IExpression            initialValue = IExpression.UNASSIGNED;
	private          IdentExpression        name;
	private          TypeModifiers          typeModifiers;
	private @NotNull TypeName               typeName     = new VariableTypeNameImpl();

	public VariableStatementImpl(final VariableSequence aSequence) {
		_parent = aSequence;
	}

	public void addAnnotation(final AnnotationClause a) {
		if (annotations == null)
			annotations = new ArrayList<AnnotationClause>();
		annotations.add(a);
	}

	@Override
	public int getColumn() {
		// TODO what about annotations
		return name.getColumn();
	}

	@Override
	public int getColumnEnd() {
		// TODO what about initialValue
		return name.getColumnEnd();
	}

	@Override
	public File getFile() {
		return name.getFile();
	}

	@Override
	public int getLine() {
		// TODO what about annotations
		return name.getLine();
	}

	@Override
	public int getLineEnd() {
		// TODO what about initialValue
		return name.getLineEnd();
	}

	@Override
	public Context getContext() {
		return getParent().getContext();
	}

	@Override
	public @NotNull String getName() {
		return name.getText();
	}

	@Override
	public IdentExpression getNameToken() {
		return name;
	}

	@Override
	public OS_Element getParent() {
		return _parent;
	}

	@Override
	public TypeModifiers getTypeModifiers() {
		return typeModifiers;
	}

	// region annotations

	@Override
	public void initial(final IExpression aExpr) {
		initialValue = aExpr;
	}

	@Override
	@NotNull
	public IExpression initialValue() {
		return initialValue;
	}

	@Override
	public void set(final TypeModifiers y) {
		typeModifiers = y;
	}

	// endregion

	// region Locatable

	@Override
	public void setName(final IdentExpression s) {
		name = s;
	}

	@Override
	public void setTypeName(@NotNull final TypeName tn) {
		typeName = tn;
	}

	@Override
	@NotNull
	public TypeName typeName() {
		return typeName;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitVariableStatement(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	public void walkAnnotations(@NotNull AnnotationWalker annotationWalker) {
		if (_parent.annotations() != null) {
			for (AnnotationClause annotationClause : _parent.annotations()) {
				for (AnnotationPart annotationPart : annotationClause.aps()) {
					annotationWalker.annotation(annotationPart);
				}
			}
		}
		if (annotations == null)
			return;
		for (AnnotationClause annotationClause : annotations) {
			for (AnnotationPart annotationPart : annotationClause.aps()) {
				annotationWalker.annotation(annotationPart);
			}
		}
	}

	// endregion
}

//
//
//

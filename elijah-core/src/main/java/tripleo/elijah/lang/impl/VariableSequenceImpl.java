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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VariableSequenceImpl implements tripleo.elijah.lang.i.VariableSequence {

	private   Context                _ctx;
	private   AccessNotation         access_note;
	@Nullable List<AnnotationClause> annotations = null;
	List<VariableStatement> stmts;
	private El_Category   category;
	private TypeModifiers def;
	private OS_Element    parent;

	@Deprecated
	public VariableSequenceImpl() {
		stmts = new ArrayList<VariableStatement>();
	}

	public VariableSequenceImpl(Context aContext) {
		stmts = new ArrayList<VariableStatement>();
		_ctx  = aContext;
	}

	@Override
	public void addAnnotation(final AnnotationClause a) {
		if (annotations == null)
			annotations = new ArrayList<AnnotationClause>();
		annotations.add(a);
	}

	@Override
	public List<AnnotationClause> annotations() {
		return annotations;
	}

	@Override
	public void defaultModifiers(final TypeModifiers aModifiers) {
		def = aModifiers;
	}

	@Override
	public El_Category getCategory() {
		return category;
	}

	@Override
	public Collection<VariableStatement> items() {
		return stmts;
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public OS_Element getParent() {
		return this.parent;
	}

	@Override
	public @NotNull VariableStatement next() {
		final VariableStatement st = new VariableStatementImpl(this);
		st.set(def);
		stmts.add(st);
		return st;
	}

	// region ClassItem

	@Override
	public void setParent(final OS_Element parent) {
		this.parent = parent;
	}

	@Override
	public void setTypeName(@NotNull TypeName aTypeName) {
		for (VariableStatement vs : stmts) {
			vs.setTypeName(aTypeName);
		}
	}

	@Override
	public void setCategory(El_Category aCategory) {
		category = aCategory;
	}

	@Override
	public void setContext(final Context ctx) {
		_ctx = ctx;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitVariableSequence(this);
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		int i = 1;
		for (final VariableStatement stmt : stmts) {
			sw.fieldString("stmt%d".formatted(i++), stmt.getName());
		}

	}

	@Override
	public AccessNotation getAccess() {
		return access_note;
	}

	// endregion

	@Override
	public String toString() {
		final List<String> r = new ArrayList<String>();
		for (final VariableStatement stmt : stmts) {
			r.add(stmt.getName());
		}
		return r.toString();
//		return (stmts.stream().map(n -> n.getName()).collect(Collectors.toList())).toString();
	}

	@Override
	public void setAccess(AccessNotation aNotation) {
		access_note = aNotation;
	}

}

//
//
//

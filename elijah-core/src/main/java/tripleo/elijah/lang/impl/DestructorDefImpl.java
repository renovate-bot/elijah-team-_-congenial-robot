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
import tripleo.elijah.contexts.FunctionContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;

/**
 * @author Tripleo
 * <p>
 * Created Apr 16, 2020 at 7:35:50 AM
 */
public class DestructorDefImpl extends BaseFunctionDef implements tripleo.elijah.lang.i.DestructorDef {

	private final ClassStatement parent;

	public DestructorDefImpl(final ClassStatement aClassStatement, final Context context) {
		parent = aClassStatement;
		if (aClassStatement instanceof OS_Container) {
			((OS_Container) parent).addToContainer(this);
		} else {
			throw new IllegalStateException("adding DestructorDef to " + aClassStatement.getClass().getName());
		}
		_a.setContext(new FunctionContext(context, this));
		setSpecies(Species.DTOR);
	}

	@Override
	public void add(final FunctionItem seq) {
		items().add((OS_NamedElement) seq);
	}

	@Override
	public void setHeader(@NotNull FunctionHeader aFunctionHeader) {
		setFal(aFunctionHeader.getFal());
//		set(aFunctionHeader.getModifier());
		assert aFunctionHeader.getModifier() == null;
		setName(aFunctionHeader.getName());
//		setReturnType(aFunctionHeader.getReturnType());
		assert aFunctionHeader.getReturnType() == null;
	}

	@Override
	public void postConstruct() {

	}

	@Override
	public @Nullable TypeName returnType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(FunctionModifiers mod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAbstract(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBody(FunctionBody aFunctionBody) {
		// TODO Auto-generated method stub

	}

	@Override
	public @Nullable OS_Element getParent() {
		return null;
	}

	@Override
	public void setReturnType(TypeName tn) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitDestructor(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnsupportedOperationException();
	}
}

//
//
//

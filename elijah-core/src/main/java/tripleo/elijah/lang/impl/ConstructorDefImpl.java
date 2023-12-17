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
import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang2.ElElementVisitor;

/**
 * @author Tripleo
 * <p>
 * Created Apr 16, 2020 at 7:34:07 AM
 */
public class ConstructorDefImpl extends BaseFunctionDef implements tripleo.elijah.lang.i.ConstructorDef {

//	private FunctionModifiers mod;

	private final OS_Element parent;

//	private @Nullable TypeName rt;

	public ConstructorDefImpl(final @Nullable IdentExpression aConstructorName, final _CommonNC aParent, final Context context) {
		parent = (OS_Element) aParent;
		if (parent != null) {
			if (aParent instanceof OS_Container) {
				((OS_Container) parent).addToContainer(this);
			} else {
				throw new IllegalStateException("adding FunctionDef to " + aParent.getClass().getName());
			}
			_a.setContext(new FunctionContext(context, this));
		}

		if (aConstructorName != null)
			setName(aConstructorName);
		else
			setName(LangGlobals.emptyConstructorName); // hack for Context#lookup
		setSpecies(Species.CTOR);

		__n = EN_Name_.create(funName.getText()); // !!
	}

	@Override
	public void add(final FunctionItem seq) {
		items().add((OS_NamedElement) seq);
	}

	@Override // OS_Element
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public void postConstruct() {

	}

	@Override
	public @Nullable TypeName returnType() {
		return rt;
	}

	@Override
	public void set(FunctionModifiers mod) {
		this.mod = mod;
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
	public void setHeader(@NotNull FunctionHeader aFunctionHeader) {
		setFal(aFunctionHeader.getFal());
//		set(aFunctionHeader.getModifier());
		assert aFunctionHeader.getModifier() == null;
		setName(aFunctionHeader.getName());
//		setReturnType(aFunctionHeader.getReturnType());
		assert aFunctionHeader.getReturnType() == null;
	}

	@Override
	public void setReturnType(TypeName tn) {
		rt = tn;
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitConstructorDef(this);
	}

	@Override
	public String toString() {
		return String.format("<Constructor %s %s %s>", parent, name(), getArgs());
	}

	@Override
	public void serializeTo(@NotNull SmallWriter sw) {
		// TODO Auto-generated method stub
		sw.fieldIdent("name", this.getNameNode());
		//throw new NotImplementedException();
	}
}

//
//
//

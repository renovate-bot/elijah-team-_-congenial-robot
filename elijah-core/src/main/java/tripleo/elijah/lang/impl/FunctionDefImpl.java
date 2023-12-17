/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Aug 30, 2005 8:43:27 PM
 *
 * $Id$
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.FunctionContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.impl.ENU_FunctionDefinition;
import tripleo.elijah.lang.nextgen.names.impl.ENU_FunctionName;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.lang2.ElElementVisitor;

// TODO FunctionDef is not a Container is it?
public class FunctionDefImpl extends BaseFunctionDef
		implements Documentable, ClassItem, tripleo.elijah.lang.i.FunctionDef {

	private final OS_Element        parent;
	private       FunctionModifiers _mod;
	private       boolean           _isAbstract;
	private       OS_FuncType       osType;
	private @Nullable TypeName _returnType = null;

	@Override
	public EN_Name getEnName() {
		if (funName == null) {
			throw new IllegalStateException("call #setName first");
		}

		return funName.getName();
	}

	@Override
	public void setName(IdentExpression aText) {
		super.setName(aText);
		getEnName().addUnderstanding(new ENU_FunctionName());
		getEnName().addUnderstanding(new ENU_FunctionDefinition(this));
	}

	public FunctionDefImpl(OS_Element element, Context context) {
		parent = element;
		if (element instanceof OS_Container) {
			((OS_Container) parent).addToContainer(this);
		} else if (element instanceof PropertyStatement) {
			// do nothing
		} else {
			throw new IllegalStateException("adding FunctionDef to " + element.getClass().getName());
		}
		_a.setContext(new FunctionContext(context, this));
	}

	@Override
	public void add(FunctionItem seq) {
		// TODO Auto-generated method stub
		throw new IllegalStateException("Error");
	}

	@Override
	public void setBody(@NotNull FunctionBody aFunctionBody) {
		scope(aFunctionBody.scope3());
		setAbstract(aFunctionBody.getAbstract());
	}

	// endregion

	@Override // OS_Element
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public void postConstruct() { // TODO

	}

	@Override
	public void setReturnType(final TypeName tn) {
		this._returnType = tn;
	}

	@Override
	public void set(final FunctionModifiers mod) {
		assert _mod == null;
		_mod = mod;
	}

	@Override
	public void setAbstract(final boolean b) {
		_isAbstract = b;
		if (b) {
			this.set(FunctionModifiers.ABSTRACT);
		}
	}

	@Override
	public void setHeader(@NotNull FunctionHeader aFunctionHeader) {
		setFal(aFunctionHeader.getFal());
		set(aFunctionHeader.getModifier());
		setName(aFunctionHeader.getName());
		setReturnType(aFunctionHeader.getReturnType());
	}

	@Override
	public @NotNull OS_FuncType getOS_Type() {
		if (osType == null)
			osType = new OS_FuncType(this);
		return osType;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitFunctionDef(this);
	}

	/**
	 * Can be {@code null} under the following circumstances:<br/>
	 * <br/>
	 * <p>
	 * 1. The compiler(parser) didn't get a chance to set it yet<br/>
	 * 2. The programmer did not specify a return value and the compiler must deduce
	 * it<br/>
	 * 3. The function is a void-type and specification isn't required <br/>
	 *
	 * @return the associated TypeName or NULL
	 */
	@Override
	public @Nullable TypeName returnType() {
		return _returnType;
	}

	@Override
	public String toString() {
		return String.format("<Function %s %s %s>", parent, name(), getArgs());
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		sw.fieldIdent("name", getNameNode());
//		throw new NotImplementedException();
	}
}

//
//
//
//
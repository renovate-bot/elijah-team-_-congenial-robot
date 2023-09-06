/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.Node;
import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

public class VariableReferenceNode3 implements IExpressionNode {

	private final String  _name;
	private final Node    _container;
	private final TypeRef _typeRef;

	public VariableReferenceNode3(final String name, final Node container, final TypeRef typeRef) {
		this._name      = name;
		_typeRef        = typeRef;
		this._container = container;
	}

	public @Nullable String getTypeName() {
		NotImplementedException.raise();
//		return _container.getName();
		return null;
	}

	@Override
	public @Nullable IExpression getExpr() {
		return null;
	}

	@Override
	public boolean is_const_expr() {
		return false;
	}

	@Override
	public boolean is_underscore() {
		return _name.equals("_");
	}

	//
	//
	//

	@Override
	public boolean is_var_ref() {
		return true;
	}

	@Override
	public boolean is_simple() {
		return true; //is_const_expr() || is_underscore(); // TODO only handles simple varrefs
	}

	@Override
	public String genText(final CompilerContext cctx) {
		return genText();
	}

	@Contract(pure = true)
	private char a() {
		if (_container == null) throw new IllegalStateException("null _container in VarRefNode3");
		//
		if (_container instanceof MethHdrNode)
			return 'a';
		if (_container instanceof CaseHdrNode)
			return 't';
		if (_container instanceof ScopeNode)
			return 'v';
		// TODO should not have this fallback
		return 'v';
	}

	@Override
	public String genType() {
		return _typeRef.genType();
	}

	@Override
	public String genText() {
//		NotImplementedException.raise();
		return String.format("v%c%s", a(), _name);
	}

	@Override
	public TypeRef getType() {
//		NotImplementedException.raise();
		return _typeRef;
	}
}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

public class VariableReferenceNode2 extends ExpressionNode {
	private final String  _type;
	private       String  _declared;
	private       boolean _perm;

	public VariableReferenceNode2(final String declared, final String t, final boolean b) {
		super();
		this._declared = declared;
		this._perm     = b;
		this._type     = t;
//		final Token ct = new CommonToken();
//		ct.setText(declared);
//		setText(new IdentExpressionImpl(ct)); // TODO why call this?
	}

	@Override
	public String genText(final CompilerContext cctx) {
		if (getExpr()/*iex*/ == null) {
			return _declared;
		} else {
			NotImplementedException.raise();
//			return super.genText(cctx);
		}
		return super.genText(cctx);
	}

	@Override
	public String genText() {
		if (_perm) return _declared;
		NotImplementedException.raise();
		return "vtn"; // TODO hardcoded
	}

//	public TypeNameNode getType() {
//		NotImplementedException.raise();
//		return null;
//	}

	private void setText(final @NotNull IdentExpression identExpression) {
		//NotImplementedException.raise();
		_declared = identExpression.getText();
		_perm     = true;
	}
}

//
//
//

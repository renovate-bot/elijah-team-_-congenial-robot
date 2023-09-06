/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 2, 2005 2:28:42 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.gen.nodes;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.gen.Node;

public class CaseHdrNode implements Node {

	private IExpressionNode expr;

	public CaseHdrNode(final VariableReferenceNode3 varref) {
		this.expr = varref;
	}

	public IExpressionNode getExpr() {
		return expr;
	}

	public void setExpr(final ExpressionNode expr) {
		this.expr = expr;
	}

	public @NotNull String simpleGenText() {
		if (expr instanceof VariableReferenceNode3) {
			return expr.genText();
		}
		// TODO mark for deletion
//		if (expr.getExpr() instanceof VariableReferenceImpl) {
//			return ((VariableReferenceImpl) expr.getExpr()).getName();
//		}
//		if (expr/*.getExpr()*/ instanceof VariableReferenceNode2) {
//			return expr.genText();
//		}
		throw new IllegalStateException("no implementation");
	}

	/**
	 * do not call
	 *
	 * @return
	 */
	@Override
	public int getCode() {
		return -1;
	}
}

//
//
//

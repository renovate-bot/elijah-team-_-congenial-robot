/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 * Created Apr 2, 2019 at 11:04:35 AM
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.util.NotImplementedException;

/**
 * @author Tripleo(sb)
 */
public class TypeAliasStatementImpl implements OS_Element, tripleo.elijah.lang.i.TypeAliasStatement {

	private final OS_Element      parent;
	private       IdentExpression x;
	private       Qualident       y;

	public TypeAliasStatementImpl(final OS_Element aParent) {
		this.parent = aParent;
	}

	@Override
	public Context getContext() {
		throw new NotImplementedException();
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public void make(final IdentExpression x, final Qualident y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setBecomes(final Qualident qq) {
		y = qq;
	}

	@Override
	public void setIdent(final IdentExpression aToken) {
		x = aToken;
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitTypeAlias(this);
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}
}

//
//
//

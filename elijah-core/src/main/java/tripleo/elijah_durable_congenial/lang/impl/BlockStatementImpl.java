/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.FormalArgList;
import tripleo.elijah_durable_congenial.lang.i.NormalTypeName;
import tripleo.elijah_durable_congenial.lang.i.Scope;
import tripleo.elijah_durable_congenial.lang.i.StatementClosure;
import tripleo.elijah_durable_congenial.lang.i.*;

// Referenced classes of package pak2:
//			Statement, StatementClosure, FormalArgList

public class BlockStatementImpl implements /* Statement, */ BlockStatement {

	private final          FormalArgList    fal = new FormalArgListImpl();
	final private          Scope            parent;
	private final @NotNull StatementClosure scope;
	private final          NormalTypeName   tn  = new RegularTypeNameImpl(); // FIXME

	public BlockStatementImpl(final Scope aParent) {
		parent = aParent;
		scope  = new AbstractStatementClosure(parent);
	}

	@Override
	public @NotNull FormalArgList opfal() {
		return fal;
	}

	@Override
	public @NotNull NormalTypeName returnType() {
		return tn;
	}

	@Override
	public @NotNull StatementClosure scope() {
		return scope;
	}
}

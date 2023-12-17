/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci;

import antlr.Token;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.lang.i.ExpressionList;
import tripleo.elijah.lang.i.OS_Module;


/**
 * @author Tripleo
 * <p>
 * Created 	Apr 15, 2020 at 4:59:21 AM
 * Created 1/8/21 7:19 AM
 */
public class CiIndexingStatementImpl implements CiIndexingStatement {

	private                 ExpressionList       exprs;
	private                 Token                name;
	private final @Nullable CompilerInstructions parent;

	public CiIndexingStatementImpl(final CompilerInstructions module) {
		this.parent = module;
	}

	public CiIndexingStatementImpl(final OS_Module module) {
		//this.parent = module;
		this.parent = null;
	}

	@Override
	public void setExprs(final ExpressionList el) {
		this.exprs = el;
	}

	@Override
	public void setName(final Token i1) {
		this.name = i1;
	}

}

//
//
//

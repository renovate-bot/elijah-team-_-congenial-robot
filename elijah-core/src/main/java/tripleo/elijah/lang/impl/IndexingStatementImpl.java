/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import tripleo.elijah.lang.i.ExpressionList;
import tripleo.elijah.lang.i.IndexingItem;
import tripleo.elijah.lang.i.OS_Module;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tripleo
 * <p>
 * Created Apr 15, 2020 at 4:59:21 AM
 */
public class IndexingStatementImpl implements tripleo.elijah.lang.i.IndexingStatement {

	private       ExpressionList     exprs;
	private final List<IndexingItem> items = new ArrayList<IndexingItem>();
	private       Token              name;
	private final OS_Module          parent;

	public IndexingStatementImpl(final OS_Module aModule) {
		parent = aModule;
	}

	@Override
	public void add(final IndexingItem i) {
		items.add(i);
	}

	@Override
	public void setExprs(final ExpressionList el) {
		exprs = el;
	}

	@Override
	public void setName(final Token i1) {
		name = i1;
	}

	/*
	 * public void setParent(OS_Module aParent) { parent = aParent; }
	 *
	 * public void setModule(final OS_Module module) { parent = module;}
	 */
}

//
//
//

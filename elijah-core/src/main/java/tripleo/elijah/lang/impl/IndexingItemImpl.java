/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 * Created Mar 27, 2019 at 1:49:23 PM
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import tripleo.elijah.lang.i.ExpressionList;

/**
 * @author Tripleo(sb)
 */
public class IndexingItemImpl implements tripleo.elijah.lang.i.IndexingItem {

	private final ExpressionList expr;
	private final Token          token;

	public IndexingItemImpl(final Token i1, final ExpressionList c1) {
		this.token = i1;
		this.expr  = c1;
	}

}

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.ExpressionList;
import tripleo.elijah.lang.i.Qualident;

/**
 * Created 8/15/20 6:45 PM
 */
public class AnnotationPartImpl implements tripleo.elijah.lang.i.AnnotationPart {
	private Qualident      _class;
	private ExpressionList _exprs;

	@Override
	public Qualident annoClass() {
		return _class;
	}

	@Override
	public ExpressionList getExprs() {
		return _exprs;
	}

	@Override
	public void setClass(final Qualident q) {
		_class = q;
	}

	@Override
	public void setExprs(final ExpressionList el) {
		_exprs = el;
	}
}

//
//
//

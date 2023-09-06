/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.Context;

/*
 * Created on 5/19/2019 at 02:09
 *
 * $Id$
 *
 */
public class AttachedImpl implements tripleo.elijah.lang.i.Attached {
	//	int     _code;
	Context _context;
//	Node    _node;

	public AttachedImpl() {
	}

	public AttachedImpl(final Context aContext) {
		_context = aContext;
	}

//	@Override
//	public int getCode() {
//		return _code;
//	}
//
//	@Override
//	public void setCode(final int aCode) {
//		_code = aCode;
//	}

	@Override
	public Context getContext() {
		return _context;
	}

	@Override
	public void setContext(final Context aContext) {
		_context = aContext;
	}

//	@Override
//	public Node getNode() {
//		return _node;
//	}
//
//	@Override
//	public void setNode(final Node aNode) {
//		_node = aNode;
//	}

}

//
//
//

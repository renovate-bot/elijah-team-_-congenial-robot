/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.IdentExpression;

import java.util.ArrayList;
import java.util.List;

public class IdentListImpl implements tripleo.elijah.lang.i.IdentList {

	// List<OS_Ident> idents=new ArrayList<OS_Ident>();
	@NotNull List<IdentExpression> idents2 = new ArrayList<IdentExpression>();

//	public void push(String aa){
//		idents.add(new OS_Ident(aa));
//	}

	@Override
	public void push(final IdentExpression s) {
		idents2.add(s);
	}
}

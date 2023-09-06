/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.util.NotImplementedException;

/*
 * Created on 5/10/2019 at 11:07
 *
 * $$Id$
 *
 */
public class MethRef {
	private final int     _code;
	private final TypeRef _parent;
	private final String  _title;

	public MethRef(final String title, final TypeRef parent, final int code) {
		_title  = title;
		_parent = parent;
		_code   = code;
	}

	public void setArgTypes(final TypeRef... types) {
		NotImplementedException.raise();
	}

	public void setReturnType(final TypeRef return_type) {
		NotImplementedException.raise();
	}

	public int getCode() {
		return _code;
	}

	public TypeRef getParent() {
		return _parent;
	}

	public String getTitle() {
		return _title;
	}
}

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on 4/23/2019 at 03:44
 *
 * $Id$
 *
 */
package tripleo.elijah.gen;

public class TypeRef implements Node {
	public static final int CODE_SYS_INT = 80;
	public static final int CODE_U64     = 81;
	private final ModuleRef   _module;
	private final RefElemenet _parent;
	private final String      _name;
	private final int         _code;

	public TypeRef(final ModuleRef moduleRef, final RefElemenet parent, final String name, final int code) {
		this._module = moduleRef;
		this._parent = parent;
		this._name   = name;
		this._code   = code;
	}

	public static boolean is_integer_code(final int aCode) {
		return aCode == CODE_U64 || aCode == CODE_SYS_INT;
	}

	public String genType() {
		return String.format("Z%d", _code);
	}

	@Override
	public int getCode() {
		return _code;
	}

	/**
	 * Returns the type name
	 *
	 * @return the type name as referenced in the code
	 * @see #genType()
	 */
	public String getName() {
		return _name;
	}

	public String genName() {
		if (_code == CODE_U64) return "Z81"; // u64
		if (_code == CODE_SYS_INT) return "Z80"; // int (SystemInteger)
		//
/*
		if (_code < 100) {
			if (_is_expanded_type)
				return String.format("Z%d", _code);
			else
				return String.format("Z%d*", _code);
		}
*/
		return String.format("Z%d", _code);
	}

}

//
//
//

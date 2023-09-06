/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Apr 23, 2019 03:43
 */
package tripleo.elijah.gen;

public class ModuleRef implements RefElemenet {
	private final String _path;
	private final int    _code;

	public ModuleRef(final String path, final int code) {
		this._path = path;
		this._code = code;
	}
}

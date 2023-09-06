/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen;

public class CompilerContext {

	private final String _module;
	private       int    _tmp = 0;

	public CompilerContext(final String aModule) {
		_module = aModule;
	}

	public String module() {
		// TODO Auto-generated method stub
//		NotImplementedException.raise();
		return _module;
	}

	public int nextTmp() {
		// TODO Auto-generated method stub
		return ++_tmp;
	}

}

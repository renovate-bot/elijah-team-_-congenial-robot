/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.functionality.f202;

/**
 * Created 8/11/21 6:01 AM
 */
public class DefaultProgressBehavior implements ProgressBehavior {
	@Override
	public void reportProgress(String a) {
		System.out.println("202 Writing " + a);
	}
}

//
//
//

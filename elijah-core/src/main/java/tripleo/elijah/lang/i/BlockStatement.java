/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

// Referenced classes of package pak2:
//			Statement, StatementClosure, FormalArgList

public interface BlockStatement extends /*Statement,*/ StatementItem {

	public FormalArgList opfal();

	public NormalTypeName returnType();

	public StatementClosure scope();
}

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang2.ElElementVisitor;

public interface OS_Element {
	Context getContext();

	@Nullable OS_Element getParent();

	void visitGen(ElElementVisitor visit);

	void serializeTo(SmallWriter sw);
}

//
//
//

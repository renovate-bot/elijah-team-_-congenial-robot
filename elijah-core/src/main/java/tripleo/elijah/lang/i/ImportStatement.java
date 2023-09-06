/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.contexts.ImportContext;
import tripleo.elijah.lang.impl.AccessNotationImpl;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.List;

public interface ImportStatement extends ModuleItem, ClassItem, StatementItem {
	List<Qualident> parts();

	void setAccess(AccessNotationImpl aNotation);

	void setContext(ImportContext ctx);

	@Override
	default void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitImportStatment(this);
	}

	@Override
	default void serializeTo(SmallWriter sw) {

	}
}

//
//
//

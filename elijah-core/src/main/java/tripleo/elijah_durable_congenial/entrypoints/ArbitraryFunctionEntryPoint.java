/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.entrypoints;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;

/**
 * Created 6/14/21 7:24 AM
 */
public class ArbitraryFunctionEntryPoint implements EntryPoint {
	FunctionDef fd;

	public ArbitraryFunctionEntryPoint(@NotNull FunctionDef aFunction) {
		final OS_Element parent = aFunction.getParent();
		if (!(parent instanceof ClassStatement || parent instanceof NamespaceStatement))
			throw new IllegalStateException("Invalid parent");
		fd = aFunction;
	}

	public FunctionDef getFunction() {
		return fd;
	}

	public OS_Element getParent() {
		return fd.getParent();
	}
}

//
//
//

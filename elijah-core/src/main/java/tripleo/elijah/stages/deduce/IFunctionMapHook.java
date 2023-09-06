/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.stages.gen_fn.EvaFunction;

import java.util.Collection;

/**
 * Created 6/8/21 1:31 AM
 */
public interface IFunctionMapHook {
	void apply(Collection<EvaFunction> aGeneratedFunctions);

	boolean matches(FunctionDef aFunctionDef);
}

//
//
//

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.contexts;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.DefFunctionDef;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.impl.ContextImpl;

/**
 * @author Tripleo
 * <p>
 * Created 	Mar 26, 2020 at 9:24:44 PM
 */
public class DefFunctionContext extends ContextImpl {

	private final DefFunctionDef carrier;

	public DefFunctionContext(final DefFunctionDef functionDef) {
		carrier = functionDef;
	}

	@Override
	public @Nullable Context getParent() {
		return null;
	}

	/**
	 * By definition should have nothing to lookup
	 *
	 * @param name
	 * @param level
	 * @param alreadySearched
	 * @return
	 */
	@Override
	public LookupResultList lookup(final String name, final int level, final LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
//		final LookupResultList Result = new LookupResultListImpl();
		alreadySearched.add(carrier.getContext());
		return getParent().lookup(name, level, Result, alreadySearched, one);
	}

}

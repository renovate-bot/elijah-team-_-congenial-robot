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
import tripleo.elijah.lang.i.CaseConditional;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.impl.ContextImpl;
import tripleo.elijah.util.NotImplementedException;

/**
 * Created 9/24/20 6:11 PM
 */
public class CaseContext extends ContextImpl {
	private final Context         _parent;
	private final CaseConditional carrier;

	public CaseContext(final Context aParent, final CaseConditional mc) {
		this._parent = aParent;
		this.carrier = mc;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());

/*
		if (carrier.getIterName() != null) {
			if (name.equals(carrier.getIterName())) { // reversed to prevent NPEs
				IdentExpression ie = carrier.getIterNameToken();
				Result.add(name, level, ie, this);
			}
		}
*/

		throw new NotImplementedException(); // carrier.singleidentcontext

/*
		if (carrier.getParent() != null) {
			final ContextImpl context = getParent();
			if (!alreadySearched.contains(context) || !one)
				context.lookup(name, level + 1, Result, alreadySearched, false); // TODO test this
		}
		return Result;
*/

	}
}

//
//
//

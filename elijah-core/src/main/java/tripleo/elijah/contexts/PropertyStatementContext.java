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
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.PropertyStatement;
import tripleo.elijah.lang.impl.ContextImpl;

/**
 * Created 12/26/20 2:24 AM
 */
public class PropertyStatementContext extends ContextImpl {

	private final Context           _parent;
	public        PropertyStatement carrier;

	public PropertyStatementContext(final Context aParent, final OS_Element element) {
		this._parent = aParent;
		this.carrier = (PropertyStatement) element;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final @NotNull String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());

		if (name.equals("get")) {
			Result.add(name, level, carrier.get_fn(), this);
		} else if (name.equals("set")) {
			Result.add(name, level, carrier.set_fn(), this);
		}

		if (getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) && !one) {
				context.lookup(name, level + 1, Result, alreadySearched, false);
			}
		}
		return Result;
	}

}

//
//
//

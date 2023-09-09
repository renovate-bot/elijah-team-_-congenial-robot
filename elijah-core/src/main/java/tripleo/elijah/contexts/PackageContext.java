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
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.ContextImpl;

/**
 * Created 8/15/20 6:32 PM
 */
public class PackageContext extends ContextImpl {
	private final Context    _parent;
	private final OS_Package carrier;

	public PackageContext(final Context aParent, OS_Package os_package) {
		_parent = aParent;
		carrier = os_package;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(this);
		for (OS_Element element : carrier.getElements()) {
			if (element instanceof final @NotNull OS_NamedElement element2) {
				if (element2.name().sameName(name)) {
					Result.add(name, level, element, this);
				}
			}
		}
		if (getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one)
				return context.lookup(name, level + 1, Result, alreadySearched, false);
		}
		return Result;
	}

	@Override
	public @NotNull OS_Module module() {
		return _parent.module();
	}
}

//
//
//

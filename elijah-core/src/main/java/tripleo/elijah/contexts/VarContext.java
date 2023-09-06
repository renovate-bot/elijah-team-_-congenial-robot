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
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah.lang.impl.ContextImpl;
import tripleo.elijah.lang.impl.VariableSequenceImpl;

/**
 * Created 8/30/20 1:39 PM
 */
public class VarContext extends ContextImpl {

	private final Context              _parent;
	private final VariableSequenceImpl carrier;

	public VarContext(final VariableSequenceImpl carrier, final Context _parent) {
		this.carrier = carrier;
		this._parent = _parent;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());

		for (final VariableStatement vs : carrier.items()) {
			if (vs.getName().equals(name)) {
				final IdentExpression ie = vs.getNameToken();
				Result.add(name, level, ie, this); // TODO getNameToken
			}
		}

		if (carrier.getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one)
				context.lookup(name, level + 1, Result, alreadySearched, false); // TODO test this
		}
		return Result;

	}

}

//
//
//

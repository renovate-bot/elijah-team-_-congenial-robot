/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang.impl.ContextImpl;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang.impl.ContextImpl;

/**
 * Created 10/6/20 4:22 PM
 */
public class MatchConditionalContext extends ContextImpl {
	private final Context              _parent;
	private final MatchConditional.MC1 carrier;

	public MatchConditionalContext(final Context parent, final MatchConditional.MC1 part) {
		this._parent = parent;
		this.carrier = part;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final @NotNull String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());

		if (carrier instanceof final MatchConditional.@NotNull MatchArm_TypeMatch carrier2) {
			if (name.equals(carrier2.getIdent().getText()))
				Result.add(name, level, carrier2, this);
		}

		for (final FunctionItem item : carrier.getItems()) {
			if (!(item instanceof ClassStatement) &&
					!(item instanceof NamespaceStatement) &&
					!(item instanceof FunctionDef) &&
					!(item instanceof VariableSequence)
			) continue;
			if (item instanceof OS_NamedElement) {
				if (((OS_NamedElement) item).name().sameName(name)) {
					Result.add(name, level, item, this);
				}
			} else if (item instanceof VariableSequence) {
//				tripleo.elijah.util.Stupidity.println_out_2("[FunctionContext#lookup] VariableSequenceImpl "+item);
				for (final VariableStatement vs : ((VariableSequence) item).items()) {
					if (vs.getName().equals(name))
						Result.add(name, level, vs, this);
				}
			}
		}

		/*if (carrier.getParent() != null)*/
		{
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one)
				context.lookup(name, level + 1, Result, alreadySearched, false);
		}
		return Result;

	}

}

//
//
//

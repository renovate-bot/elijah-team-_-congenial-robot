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
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.lang.impl.ContextImpl;
import tripleo.elijah.lang.impl.VariableSequenceImpl;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

/**
 * Created 8/21/20 3:16 AM
 */
public class IfConditionalContext extends ContextImpl implements Context {
	private final           Context       _parent;
	private final @Nullable Context       _prev_ctx;
	private final           IfConditional carrier;

	public IfConditionalContext(final Context cur, final IfConditional ifConditional) {
		_parent   = cur;
		carrier   = ifConditional;
		_prev_ctx = null; // TOP if statement
	}

	public IfConditionalContext(final @NotNull Context ctx, final IfConditional ifConditional, final boolean _ignored) {
		_prev_ctx = ctx;
		_parent   = ((IfConditionalContext) ctx)._parent;
		carrier   = ifConditional;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(carrier.getContext());
		for (final OS_Element/*StatementItem*/ item : carrier.getItems()) {
			if (!(item instanceof ClassStatement) &&
					!(item instanceof NamespaceStatement) &&
					!(item instanceof FunctionDef) &&
					!(item instanceof VariableSequenceImpl) &&
					!(item instanceof AliasStatementImpl)
			) continue;
			if (item instanceof OS_NamedElement) {
				if (((OS_NamedElement) item).name().sameName(name)) {
					Result.add(name, level, item, this);
				}
			}
			if (item instanceof VariableSequenceImpl) {
				SimplePrintLoggerToRemoveSoon.println_out_2("1102 " + item);
				for (final VariableStatement vs : ((VariableSequenceImpl) item).items()) {
					if (vs.getName().equals(name))
						Result.add(name, level, vs, this);
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
}

//
//
//

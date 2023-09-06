package tripleo.elijah.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.impl.ContextImpl;

/**
 * Created 8/30/20 6:51 PM
 */
public class SingleIdentContext extends ContextImpl {
	private final Context         _parent;
	public        IdentExpression carrier;
	private final OS_Element      element;

	public SingleIdentContext(final Context _parent, final OS_Element element) {
		this._parent = _parent;
		this.element = element;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final @NotNull LookupResultList Result, final @NotNull SearchList alreadySearched, final boolean one) {
		alreadySearched.add(element.getContext());

		if (carrier != null && carrier.getText().equals(name))
			Result.add(name, level, element, this);

		if (getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) && !one)
				context.lookup(name, level + 1, Result, alreadySearched, false);
		}
		return Result;
	}

	public void setString(final IdentExpression carrier) {
		this.carrier = carrier;
	}
}

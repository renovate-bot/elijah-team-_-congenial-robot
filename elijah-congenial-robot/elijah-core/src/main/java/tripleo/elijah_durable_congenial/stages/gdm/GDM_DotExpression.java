package tripleo.elijah_durable_congenial.stages.gdm;

import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.lang.i.DotExpression;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn.IdentTableEntry;

import java.util.function.Consumer;

public class GDM_DotExpression implements GDM_Item {
	private final GenerateFunctions         generateFunctions;
	private final DotExpression             dotExpression;
	private final Eventual<IdentTableEntry> _p_IdentTableEntry = new Eventual<>();

	public GDM_DotExpression(final GenerateFunctions aGenerateFunctions, final DotExpression aDotExpression) {
		generateFunctions = aGenerateFunctions;
		dotExpression     = aDotExpression;
	}

	public void onIdentTableEntry(final Consumer<IdentTableEntry> ic) {
		_p_IdentTableEntry.then(ic::accept);
	}

	public void resolveIdentTableEntry(final IdentTableEntry ite) {
		_p_IdentTableEntry.resolve(ite);
	}
}

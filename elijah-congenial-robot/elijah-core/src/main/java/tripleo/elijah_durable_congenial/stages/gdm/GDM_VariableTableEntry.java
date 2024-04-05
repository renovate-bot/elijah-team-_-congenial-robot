package tripleo.elijah_durable_congenial.stages.gdm;

import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;

public class GDM_VariableTableEntry implements GDM_Item {
	private final GenerateFunctions  generateFunctions;
	private final VariableTableEntry variableTableEntry;

	public GDM_VariableTableEntry(final GenerateFunctions aGenerateFunctions, final VariableTableEntry aVariableTableEntry) {
		generateFunctions = aGenerateFunctions;
		variableTableEntry = aVariableTableEntry;

		link();
	}

	private void link() {
		variableTableEntry.elementPromise(this::link_success, (x)->{});
	}

	private void link_success(OS_Element el) {
		if (el instanceof IdentExpression ie) {
			final GDM_IdentExpression mie = generateFunctions.monitor(ie);
			mie.resolveVariableTableEntry(variableTableEntry);
		}
	}

	public void resolveDrIdent(final DR_Ident aDrIdent) {
		NotImplementedException.raise_stop();
	}

	//private Eventual<IdentTableEntry> _p_IdentTableEntry = new Eventual<>();
	//
	//public void onIdentTableEntry(final Consumer<IdentTableEntry> ic) {
	//	_p_IdentTableEntry.then(ic::accept);
	//}
	//
	//public void resolveIdentTableEntry(final IdentTableEntry ite) {
	//	_p_IdentTableEntry.resolve(ite);
	//}

}

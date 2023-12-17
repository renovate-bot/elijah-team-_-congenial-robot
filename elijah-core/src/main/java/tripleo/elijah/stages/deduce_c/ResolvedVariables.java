package tripleo.elijah.stages.deduce_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.gen_fn.EvaContainer;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.util.Maybe;

import static tripleo.elijah.util.Helpers.List_of;

public class ResolvedVariables {
	final          IdentTableEntry identTableEntry;
	final @NotNull OS_Element      parent; // README tripleo.elijah.lang._CommonNC, but that's package-private
	final          String          varName;

	public ResolvedVariables(IdentTableEntry aIdentTableEntry, @NotNull OS_Element aParent, String aVarName) {
		assert aParent instanceof ClassStatement || aParent instanceof NamespaceStatement;

		identTableEntry = aIdentTableEntry;
		parent          = aParent;
		varName         = aVarName;
	}

	public void handle(final EvaContainer evaContainer) {
		final @NotNull Maybe<EvaContainer.VarTableEntry> variable_m = evaContainer.getVariable(varName);

		if (!variable_m.isException()) {
			// TODO 11/10 curiously this is required to remove squigglies
			//  when if above (maybe irt idea, but should be always by dfa)
			//  and @NotNull below specify it
			assert variable_m.o != null;
			final @NotNull EvaContainer.VarTableEntry variable = variable_m.o;

			final TypeTableEntry type = identTableEntry.type;
			if (type != null) {
				variable.addPotentialTypes(List_of(type));
			}
			variable.addPotentialTypes(identTableEntry.potentialTypes());
			variable.updatePotentialTypes(evaContainer);
		} else {
			throw new AssertionError();
		}

	}
}

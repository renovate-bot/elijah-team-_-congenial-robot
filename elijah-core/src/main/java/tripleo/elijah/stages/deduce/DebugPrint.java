package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.stages.deduce.declarations.DeferredMember;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;

public class DebugPrint {
	public static void addDependentType(final BaseEvaFunction aGeneratedFunction, final GenType aGenType) {
		System.err.println("**** addDependentType " + aGeneratedFunction + " " + aGenType);
	}

	public static void addDeferredMember(final DeferredMember aDm) {
		System.err.println("**** addDeferredMember " + aDm);
	}

	public static void addPotentialType(final @NotNull VariableTableEntry aVte, final ConstantTableEntry aCte) {
		var c = aVte._deduceTypes2().module.getCompilation();
		if (c.reports().outputOn(Finally.Outs.Out_2121)) {
			System.err.println("**** addPotentialType " + aVte + " " + aCte);
		}
	}
}

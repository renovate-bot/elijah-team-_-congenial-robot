package tripleo.elijah_durable_congenial.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.Finally;
import tripleo.elijah.stages.deduce.declarations.DeferredMember;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.ConstantTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_durable_congenial.comp.Finally;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.stages.gen_fn.VariableTableEntry;

public enum DebugPrint {
	;

	public static void addDependentType(final BaseEvaFunction aGeneratedFunction, final GenType aGenType) {
		System.err.println("**** addDependentType " + aGeneratedFunction + " " + aGenType);
	}

	public static void addDeferredMember(final DeferredMember aDm) {
		System.err.println("**** addDeferredMember " + aDm);
	}

	public static void addPotentialType(final @NotNull VariableTableEntry aVte, final ConstantTableEntry aCte) {
		var c = aVte._deduceTypes2().module.getCompilation();
		var REPORTS = c.reports();
		if (REPORTS.outputOn(Finally.Outs.Out_2121)) {
			System.err.println("**** addPotentialType " + aVte + " " + aCte);
		}
	}
}

package tripleo.elijah.stages.deduce;

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

	public static void addPotentialType(final VariableTableEntry aVte, final ConstantTableEntry aCte) {
		System.err.println("**** addPotentialType " + aVte + " " + aCte);
	}
}

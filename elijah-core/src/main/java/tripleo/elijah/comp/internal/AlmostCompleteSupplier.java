package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.i.CR_Action;

public class AlmostCompleteSupplier {
	public static CR_ActionSupplier of() {
		return new CR_ActionSupplier() {
			@Override
			public CR_Action get() {
				return new CR_AlmostComplete();
			}
		};
	}
}
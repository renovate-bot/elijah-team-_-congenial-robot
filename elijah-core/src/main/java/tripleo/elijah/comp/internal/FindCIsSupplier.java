package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.i.CR_Action;

public class FindCIsSupplier {
	public static CR_ActionSupplier of(final CompilationRunner aCompilationRunner) {
		return new CR_ActionSupplier() {
			@Override
			public CR_Action get() {
				return aCompilationRunner.cr_find_cis();
			}
		};
	}
}

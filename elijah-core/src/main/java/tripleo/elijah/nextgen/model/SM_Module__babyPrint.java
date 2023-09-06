package tripleo.elijah.nextgen.model;

import org.jetbrains.annotations.NotNull;

public enum SM_Module__babyPrint {
	;

	public static void babyPrint(final @NotNull SM_Module sm) {
		for (final SM_ModuleItem item : sm.items()) {
			System.out.println(String.valueOf(item));
		}
	}
}

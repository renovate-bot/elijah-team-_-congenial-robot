package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;

interface GCR_Rule {
	public static @NotNull GCR_Rule withMessage(@NotNull String message) {
		return new GCR_Rule() {
			@Override
			public String text() {
				return message;
			}
		};
	}

	String text();
}

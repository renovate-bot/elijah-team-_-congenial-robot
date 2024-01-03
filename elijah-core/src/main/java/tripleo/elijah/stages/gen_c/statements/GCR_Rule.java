package tripleo.elijah.stages.gen_c.statements;

import org.jetbrains.annotations.NotNull;

public interface GCR_Rule {
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

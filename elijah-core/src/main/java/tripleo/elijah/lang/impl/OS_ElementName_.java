package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.OS_ElementName;

@SuppressWarnings({"UtilityClassCanBeEnum", "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass"})
public class OS_ElementName_ {
	private OS_ElementName_() {
	}

	public static OS_ElementName empty() {
		return new OS_ElementName_Empty();
	}

	public static OS_ElementName ofString(String text) {
		return new OS_ElementName_ofString(text);
	}

	private static class OS_ElementName_Empty implements OS_ElementName {
		@Override
		public String toString() {
			return "[OS_ElementName: EMPTY]";
		}

		@Override
		public String asString() {
			return "";
		}

		@Override
		public boolean sameName(String name) {
			return name.isEmpty();
		}
	}

	private static class OS_ElementName_ofString implements OS_ElementName {
		private final String text;

		public OS_ElementName_ofString(final String aText) {
			text = aText;
		}

		@Override
		public String asString() {
			return text;
		}

		@Override
		public boolean sameName(String name) {
			return text.equals(name);
		}

		@Override
		public String toString() {
			final String sb = "[OS_ElementName: %s]".formatted(text);
			return sb;
		}
	}
}

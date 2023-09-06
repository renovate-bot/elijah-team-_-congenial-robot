package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_OutputString;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CB_Output {
	private final List<CB_OutputString> x = new ArrayList<>();

	public @NotNull List<CB_OutputString> get() {
		return x;
	}

	public void logProgress(final int number, final String text) {
		if (number == 130) return;

//		System.err.println
		print(MessageFormat.format("{0} {1}", number, text));
	}

	void print(final String s) {
		x.add(() -> s);
	}
}

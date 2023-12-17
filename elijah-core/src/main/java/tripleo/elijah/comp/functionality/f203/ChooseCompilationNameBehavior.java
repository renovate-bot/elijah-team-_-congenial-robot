package tripleo.elijah.comp.functionality.f203;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;

import java.io.File;

public class ChooseCompilationNameBehavior implements ChooseDirectoryNameBehavior {
	private final Compilation c;

	public ChooseCompilationNameBehavior(final Compilation aC) {
		c = aC;
	}

	@Override
	public @NotNull File chooseDirectory() {
		final String c_name = c.getCompilationNumberString();

		final File fn00 = new File("COMP", c_name);
//		final File fn0 = new File(fn00, "date");

		return fn00;
	}
}

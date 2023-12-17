package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_OutputString;
import tripleo.elijah.comp.notation.GN_Notable;

import java.util.ArrayList;
import java.util.List;

class NotableAction implements CB_Action {
	private final @NotNull GN_Notable            notable;
	@NotNull
	final                  List<CB_OutputString> o;

	public NotableAction(final @NotNull GN_Notable aNotable) {
		notable = aNotable;
		o       = new ArrayList<>();
	}

	public void _actual_run() {
		notable.run();
	}

	@Override
	public void execute() {
		if (false) notable.run();
	}

	@Override
	public @NotNull String name() {
		return "Notable wrapper";
	}

	@Override
	public List<CB_OutputString> outputStrings() {
		return o;
	}
}

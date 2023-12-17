package tripleo.elijah.comp.internal;

import static tripleo.elijah.util.Helpers.List_of;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_Process;

class SingleActionProcess implements CB_Process {
	final CB_Action a;

	public SingleActionProcess(final CB_Action aAction) {
		a = aAction;
	}

	@Override
	public @NotNull List<CB_Action> steps() {
		return List_of(a);
	}
}

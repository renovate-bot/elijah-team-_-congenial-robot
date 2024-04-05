package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.i.CB_Action;
import tripleo.elijah_durable_congenial.comp.i.CB_Process;

import java.util.List;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

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

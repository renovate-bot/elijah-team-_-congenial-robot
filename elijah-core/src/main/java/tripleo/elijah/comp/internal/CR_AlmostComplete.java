package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CR_Action;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CR_AlmostComplete implements CR_Action {
	private CompilationRunner compilationRunner;

	@Override
	public void attach(final @NotNull CompilationRunner cr) {
		compilationRunner = cr;
	}

	@Override
	public @NotNull Operation<Ok> execute(final CR_State st, final CB_Output aO) {
		compilationRunner.cis.almostComplete();
		return Operation.success(Ok.instance());
	}

	@Override
	public @NotNull String name() {
		return "cis almostComplete";
	}
}

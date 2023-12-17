package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_OutputString;
import tripleo.elijah.comp.i.CR_Action;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

class CB_FindCIs implements CB_Action {
	private final CompilationRunner   compilationRunner;
	private final List<CompilerInput> _inputs;

	@Contract(pure = true)
	public CB_FindCIs(final CompilationRunner aCompilationRunner, final List<CompilerInput> aInputs) {
		compilationRunner = aCompilationRunner;
		_inputs           = aInputs;

		o = compilationRunner._accessCompilation().getCompilationEnclosure().getCB_Output(); //new CB_Output();
	}

	private final CB_Output o;

	@Override
	public void execute() {
		final List<CR_Action> crActionList = List_of(compilationRunner.cr_find_cis(), new CR_AlmostComplete());

		for (final CR_Action action : crActionList) {
			action.attach(compilationRunner);
			action.execute(compilationRunner.crState, o);
		}

		for (final CB_OutputString outputString : o.get()) {
			//08/13
			SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: outputString :: " + outputString.getText());
		}
	}

	//private Compilation _accessCompilation() {
	//	return compilationRunner._accessCompilation();
	//}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "FindCIs";
	}

	@Override
	public @NotNull List<CB_OutputString> outputStrings() {
		return o.get();
	}
}

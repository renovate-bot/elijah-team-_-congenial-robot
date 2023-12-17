package tripleo.elijah.comp.internal;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_OutputString;
import tripleo.elijah.comp.i.CompilationEnclosure;

public class CB_FindStdLibActionProcess implements CB_Action {
	private final SingleActionProcess _process;
	private final CB_Action           _action;

	private final List<CB_OutputString> o = new ArrayList<>();

	public CB_FindStdLibActionProcess(final CompilationEnclosure aCe, final CR_State aCrState) {
		this._action  = new CB_FindStdLibAction(aCe, aCrState);
		this._process = new SingleActionProcess(_action);
	}

	@Override
	public void execute() {
		_process.a.execute();
	}

	@Override
	public String name() {
		return "CB_FindStdLibActionProcess";
	}

	@Override
	public @NotNull List<CB_OutputString> outputStrings() {
		return o;
	}
}

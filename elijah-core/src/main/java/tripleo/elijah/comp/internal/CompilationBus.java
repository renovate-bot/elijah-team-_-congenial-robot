package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.List;

public class CompilationBus implements ICompilationBus {
	public final @NotNull  CompilerDriver   cd;
	private final @NotNull Compilation      c;
	private final @NotNull List<CB_Process> _processes           = new ArrayList<>();
	private final @NotNull IProgressSink    _defaultProgressSink = new DefaultProgressSink();
	public                 CB_FindCIs              cb_findCIs;

	public CompilationBus(final @NotNull CompilationEnclosure ace) {
		c  = ace.getCompilationAccess().getCompilation();
		cd = new CompilerDriver(this);

		ace.setCompilerDriver(cd);
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		_processes.add(new SingleActionProcess(action));
	}

	@Override
	public IProgressSink defaultProgressSink() {
		return _defaultProgressSink;
	}

	@Override
	public CompilerDriver getCompilationDriver() {
		return cd;
	}

	@Override
	public void add(final @NotNull CB_Process aProcess) {
		_processes.add(aProcess);
	}

	@Override
	public void inst(final @NotNull ILazyCompilerInstructions aLazyCompilerInstructions) {
		_defaultProgressSink.note(IProgressSink.Codes.LazyCompilerInstructions_inst, ProgressSinkComponent.CompilationBus_, -1, new Object[]{aLazyCompilerInstructions.get()});
	}

	@Override
	public void option(final @NotNull CompilationChange aChange) {
		aChange.apply(c);
	}

	@Override
	public List<CB_Process> processes() {
		return _processes;
	}

	public void runProcesses() {
		List<CB_Process> processes = _processes;
		int              size      = 0;

		while (size < processes.size()) {
			for (int i = size; i < processes.size(); i++) {
				final CB_Process process = processes.get(i);

				process.steps().stream().forEach(aCBAction -> aCBAction.execute());
			}

			size = processes.size();
		}
		assert processes.size() == size;
	}

	//private static class DefaultProgressSink implements IProgressSink {
	//	@Override
	//	public void note(final Codes aCode, final @NotNull ProgressSinkComponent aProgressSinkComponent, final int aType, final Object[] aParams) {
	//		SimplePrintLoggerToRemoveSoon.println_err_2(aProgressSinkComponent.printErr(aCode, aType, aParams));
	//	}
	//}
}

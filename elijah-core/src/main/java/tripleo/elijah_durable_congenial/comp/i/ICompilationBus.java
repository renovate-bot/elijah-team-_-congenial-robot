package tripleo.elijah_durable_congenial.comp.i;

import tripleo.elijah_durable_congenial.comp.internal.CompilerDriver;

import java.util.List;

public interface ICompilationBus {
	void add(CB_Action aCBAction);

	IProgressSink defaultProgressSink();

	CompilerDriver getCompilationDriver();

	void add(CB_Process aProcess);

	void inst(ILazyCompilerInstructions aLazyCompilerInstructions);

	void option(CompilationChange aChange);

	List<CB_Process> processes();
}

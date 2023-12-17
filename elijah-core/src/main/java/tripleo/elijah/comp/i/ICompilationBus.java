package tripleo.elijah.comp.i;

import tripleo.elijah.comp.internal.CompilerDriver;

import java.util.List;

public interface ICompilationBus {
	void add(CB_Action aCBAction);

	IProgressSink defaultProgressSink();

	CompilerDriver getCompilationDriver();

	class COutputString implements CB_OutputString {

		private final String _text;

		public COutputString(final String aText) {
			_text = aText;
		}

		@Override
		public String getText() {
			return _text;
		}
	}

	void add(CB_Process aProcess);

	void inst(ILazyCompilerInstructions aLazyCompilerInstructions);

	void option(CompilationChange aChange);

	List<CB_Process> processes();

}

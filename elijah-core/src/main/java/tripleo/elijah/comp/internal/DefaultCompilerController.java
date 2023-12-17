package tripleo.elijah.comp.internal;

import java.util.List;

import tripleo.elijah.comp.ApacheOptionsProcessor;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.CompilerInstructionsObserver;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.CompilerController;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.OptionsProcessor;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

public class DefaultCompilerController implements CompilerController {
	List<String> args;
	String[]     args2;
	private Compilation c;
	CompilationBus      cb;
	List<CompilerInput> inputs;
	private Nov14Impl __nov14;

	@Override
	public void _setInputs(final Compilation aCompilation, final List<CompilerInput> aInputs) {
		c      = aCompilation;
		inputs = aInputs;
	}

	public void _setInputs(final List<CompilerInput> aInputs) {
		inputs = aInputs;
	}

	public void hook(final CompilationRunner aCr) {

	}

	@Override
	public void printUsage() {
		SimplePrintLoggerToRemoveSoon.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public void processOptions() {
		final OptionsProcessor             op                   = new ApacheOptionsProcessor();
		final CompilerInstructionsObserver cio                  = new CompilerInstructionsObserver(c);
		final DefaultCompilationAccess     ca                   = new DefaultCompilationAccess(c);
		final CompilationEnclosure         compilationEnclosure = c.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(ca);

		cb = new CompilationBus(compilationEnclosure);

		compilationEnclosure.setCompilationBus(cb);

		c._cis()._cio = cio;

		try {
			args2 = op.process(c, inputs, cb);
		} catch (final Exception e) {
			c.getErrSink().exception(e);
			throw new RuntimeException(e);
		}
	}

	interface __Nov14 {

	}

	class Nov14Impl implements __Nov14 {

		public Nov14Impl(CompilationEnclosure ce) {
			// TODO Auto-generated constructor stub
		}

	}

	@Override
	public void runner() {

		c.subscribeCI(c._cis()._cio);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

		this.__nov14 = new Nov14Impl(ce);

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		final CR_State          crState = new CR_State(compilationAccess);
		final CompilationRunner cr      = new CompilationRunner(compilationAccess, crState);

		crState.setRunner(cr);
		ce.setCompilationRunner(cr);

		hook(cr);

		var inputTree = c.getInputTree();

		for (CompilerInput input : inputs) {
			if (input.isNull()) // README filter out args
				inputTree.addNode(input);
		}

		cb.add(new CB_FindCIs(cr, inputs));
		cb.add(new CB_FindStdLibActionProcess(ce, crState));

		cb.runProcesses();
	}
}

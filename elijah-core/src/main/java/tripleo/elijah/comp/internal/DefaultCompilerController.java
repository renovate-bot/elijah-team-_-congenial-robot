package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;

import java.util.List;

public class DefaultCompilerController implements CompilerController {
	List<String> args;
	String[]     args2;
	private Compilation c;
	CompilationBus      cb;
	List<CompilerInput> inputs;

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
		tripleo.elijah.util.Stupidity.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
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

	@Override
	public void runner() {
		c.subscribeCI(c._cis()._cio);

		final CompilationEnclosure ce = c.getCompilationEnclosure();

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
		cb.add(new CB_FindStdLibAction(ce, crState).process());

		cb.runProcesses();
	}
}

package tripleo.elijah_durable_congenial.comp.internal;

import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.comp.ApacheOptionsProcessor;
import tripleo.elijah_durable_congenial.comp.CompilerInput;
import tripleo.elijah_durable_congenial.comp.CompilerInstructionsObserver;
import tripleo.elijah_durable_congenial.comp.i.*;

import java.util.List;

public class DefaultCompilerController implements CompilerController {
	private List<String> args;
	private String[]     args2;
	private Compilation c;
	private CompilationBus      cb;
	private List<CompilerInput> inputs;


	public void hook(final CompilationRunner aCr) {

	}

	@Override
	public void printUsage() {
		SimplePrintLoggerToRemoveSoon.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public void processOptions() {
		final OptionsProcessor             op  = new ApacheOptionsProcessor();
		final CompilerInstructionsObserver cio = new CompilerInstructionsObserver(c);
		final DefaultCompilationAccess     ca  = new DefaultCompilationAccess(c);
		final CompilationEnclosure         compilationEnclosure = c.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(ca);

		cb = new CompilationBus(compilationEnclosure);

		compilationEnclosure.setCompilationBus(cb);

		c.cp_set(CPx.cis, cio);

		try {
			args2 = op.process(c, inputs, cb);
		} catch (final Exception e) {
			c.getErrSink().exception(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void runner() {
		c.subscribeCI(c.get_cp(CPx.cis));

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
		cb.add(new CB_FindStdLibActionProcess(ce, crState));

		cb.runProcesses();
	}

	@Override
	public void setInputs(final Compilation aCompilation, final List<CompilerInput> aInputs) {
		c      = aCompilation;
		inputs = aInputs;
	}
}

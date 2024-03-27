package tripleo.elijah_durable_congenial.stages.hooligan.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_Input;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah_durable_congenial.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HooliganPipelineImpl {
	public void run(final @NotNull Compilation compilation) {
		final Hooligan        hooligan = new Hooligan();
		final List<OS_Module> modules  = new ArrayList<>();

		// FIXME 11/12 get rid of this...
		compilation.eachModule(modules::add);

		final Hooligan.SmallWriter1 sw       = hooligan.__modules2(modules);
		final EOT_OutputTree        cot      = compilation.getOutputTree();
		final List<EIT_Input>       inputs   = inputs_for_modules(modules, compilation);
		final String                text     = sw.getText();
		final EG_Statement          seq      = EG_Statement.of(text, EX_Explanation.withMessage("modules-sw-writer"));
		final EOT_OutputFile        off      = new EOT_OutputFile(inputs, "modules-sw-writer", EOT_OutputType.SWW, seq);

		cot.add(off);
	}

	private @NotNull List<EIT_Input> inputs_for_modules(final @NotNull List<OS_Module> aModules, final Compilation c) {
		return aModules.stream()
				.map(mod -> new EIT_ModuleInput(mod, c))
				.collect(Collectors.toList());
	}
}

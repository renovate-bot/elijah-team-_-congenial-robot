package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.OptionsProcessor;
import tripleo.elijah.comp.impl.CC_SetDoOut;
import tripleo.elijah.comp.impl.CC_SetShowTree;
import tripleo.elijah.comp.impl.CC_SetSilent;
import tripleo.elijah.comp.impl.CC_SetStage;
import tripleo.elijah.comp.internal.CompilationBus;
import tripleo.vendor.org.apache.commons.cli.CommandLine;
import tripleo.vendor.org.apache.commons.cli.CommandLineParser;
import tripleo.vendor.org.apache.commons.cli.DefaultParser;
import tripleo.vendor.org.apache.commons.cli.Options;

import java.util.List;
import java.util.stream.Collectors;

public class ApacheOptionsProcessor implements OptionsProcessor {
	private final CommandLineParser clp     = new DefaultParser();
	private final Options           options = new Options();

	@Contract(pure = true)
	public ApacheOptionsProcessor() {
		options.addOption("s", true, "stage: E: parse; O: output");
		options.addOption("showtree", false, "show tree");
		options.addOption("out", false, "make debug files");
		options.addOption("silent", false, "suppress DeduceType output to console");
	}

	@Override
	public String[] process(final @NotNull Compilation c, final @NotNull List<CompilerInput> aInputs, final CompilationBus aCb) throws Exception {

		final List<String> args = aInputs.stream()
				.map(inp -> inp.getInp())
				.collect(Collectors.toList());


		final CommandLine cmd;

		//cmd = clp.parse(options, args.toArray(new String[args.size()]));
		cmd = clp.parse(options, aInputs);

		if (cmd.hasOption("s")) {
			new CC_SetStage(cmd.getOptionValue('s')).apply(c);
		}
		if (cmd.hasOption("showtree")) {
			new CC_SetShowTree(true).apply(c);
		}
		if (cmd.hasOption("out")) {
			new CC_SetDoOut(true).apply(c);
		}

		if (Compilation.isGitlab_ci() || cmd.hasOption("silent")) {
			new CC_SetSilent(true).apply(c);
		}

		return cmd.getArgs();
	}
}

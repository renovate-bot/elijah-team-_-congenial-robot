package tripleo.elijah.comp;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.String_join;

class MesonFile implements EG_Statement {

	private final WriteMesonPipeline                     writeMesonPipeline;
	private final String                                 aSub_dir;
	private final Multimap<CompilerInstructions, String> lsp_outputs;
	private final CompilerInstructions                   compilerInstructions;
	private final CP_Path                                path;

	MesonFile(final WriteMesonPipeline aWriteMesonPipeline, final String aASubDir, final Multimap<CompilerInstructions, String> aLspOutputs, final CompilerInstructions aCompilerInstructions, final CP_Path aPath) {
		writeMesonPipeline   = aWriteMesonPipeline;
		aSub_dir             = aASubDir;
		lsp_outputs          = aLspOutputs;
		compilerInstructions = aCompilerInstructions;
		path                 = aPath;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("MesonFile");
	}

	@Override
	public @NotNull String getText() {
		final Collection<String> files_ = lsp_outputs.get(compilerInstructions);
		final Set<String> files = files_.stream()
				.filter(x -> x.endsWith(".c"))
				.map(x -> String.format("\t'%s',", writeMesonPipeline.pullFileName(x)))
				.collect(Collectors.toUnmodifiableSet());

		final StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s_sources = files(\n%s\n)", aSub_dir, String_join("\n", files)));
		sb.append("\n");
		sb.append(String.format("%s = static_library('%s', %s_sources, install: false,)", aSub_dir, aSub_dir, aSub_dir)); // include_directories, dependencies: [],
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%s_dep = declare_dependency( link_with: %s )", aSub_dir, aSub_dir)); // include_directories
		sb.append("\n");

		final String s = sb.toString();
		return s;
	}

	public @NotNull String getPathString() {
		return path.getPath().toString();
	}

	public CP_Path getPath() {
		return path;
	}
}

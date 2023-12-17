/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.i.CompilerInstructions;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.internal.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_generic.DoubleLatch;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.util.io.CharSink;
import tripleo.util.io.FileCharSink;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;
import static tripleo.elijah.util.Helpers.String_join;

/**
 * Created 9/13/21 11:58 PM
 */
public class WriteMesonPipeline implements PipelineMember, @NotNull Consumer<Supplier<Old_GenerateResult>> {
	final                  Pattern         pullPat               = Pattern.compile("/[^/]+/(.+)");
	private final @NotNull Compilation     c;
	private final @NotNull IPipelineAccess pa;
	private final          WritePipeline                                       writePipeline;
	@NotNull               DoubleLatch<Multimap<CompilerInstructions, String>> write_makefiles_latch = new DoubleLatch<>(this::write_makefiles_action);
	private                Consumer<Multimap<CompilerInstructions, String>>    _wmc;
	private                Supplier<Old_GenerateResult>                        grs;

	public WriteMesonPipeline(final @NotNull IPipelineAccess pa0) {
		final AccessBus     ab             = pa0.getAccessBus();
		final Compilation   compilation    = ab.getCompilation();
		final WritePipeline writePipeline1 = ab.getPipelineAccess().getWitePipeline();

		pa            = pa0;
		c             = compilation;
		writePipeline = writePipeline1;
	}

	@Override
	public void accept(final @NotNull Supplier<Old_GenerateResult> aGenerateResultSupplier) {
		final GenerateResult gr = aGenerateResultSupplier.get();
		//08/13 System.err.println("WMP66 "+gr);
		grs = aGenerateResultSupplier;
		int y = 2;
	}

	public @NotNull Consumer<Supplier<Old_GenerateResult>> consumer() {
		return new Consumer<Supplier<Old_GenerateResult>>() {
			@Override
			public void accept(final Supplier<Old_GenerateResult> aGenerateResultSupplier) {
				if (grs != null) {
					SimplePrintLoggerToRemoveSoon.println_err_2("234 grs not null " + grs.getClass().getName());
					return;
				}

				assert false;
				grs = aGenerateResultSupplier;
				//final GenerateResult gr = aGenerateResultSupplier.get();
			}
		};
	}

	@Nullable String pullFileName(@NotNull String aFilename) {
		//return aFilename.substring(aFilename.lastIndexOf('/')+1);
		Matcher x = pullPat.matcher(aFilename);
		try {
			if (x.matches())
				return x.group(1);
		} catch (IllegalStateException ignored) {
		}
		return null;
	}

	private void write_makefiles_action(final @NotNull Multimap<CompilerInstructions, String> lsp_outputs) {
		List<String> dep_dirs = new LinkedList<String>();

		try {
			write_root(lsp_outputs, dep_dirs);

			for (final CompilerInstructions compilerInstructions : lsp_outputs.keySet()) {
				final String sub_dir = compilerInstructions.getName();
				//final Path   dpath   = getPath2(sub_dir);

				getPath2(sub_dir).getPathPromise().then(dpath -> {
					if (dpath.toFile().exists()) {
						try {
							write_lsp(lsp_outputs, compilerInstructions, sub_dir);
						} catch (IOException aE) {
							throw new RuntimeException(aE);
						}
					}
				});
			}

			write_prelude();
		} catch (IOException aE) {
			throw new RuntimeException(aE);
		}
	}

	private void write_root(@NotNull Multimap<CompilerInstructions, String> lsp_outputs, @NotNull List<String> aDep_dirs) throws IOException {
		final CP_Path path2_ = getPath2("meson.build");

		path2_.getPathPromise().then(path2 -> {
			CharSink root_file = null;
			try {
				root_file = c.getIO().openWrite(path2);
			} catch (IOException aE) {
				throw new RuntimeException(aE);
			}
			try {
				String project_name   = c.getProjectName();
				String project_string = String.format("project('%s', 'c', version: '1.0.0', meson_version: '>= 0.48.0',)", project_name);
				root_file.accept(project_string);
				root_file.accept("\n");

				for (CompilerInstructions compilerInstructions : lsp_outputs.keySet()) {
					String name = compilerInstructions.getName();
					//final Path dpath = getPath2(name);

					final CharSink finalRoot_file = root_file;
					path2_.child(name).getPathPromise().then(dpath -> {
																 if (dpath.toFile().exists()) {
																	 String name_subdir_string = String.format("subdir('%s')\n", name);
																	 finalRoot_file.accept(name_subdir_string);
																	 aDep_dirs.add(name);
																 }
															 }
															);
				}
				aDep_dirs.add("Prelude");
//			String prelude_string = String.format("subdir(\"Prelude_%s\")\n", /*c.defaultGenLang()*/"c");
				String prelude_string = "subdir('Prelude')\n";
				root_file.accept(prelude_string);

//			root_file.accept("\n");

				String deps_names = String_join(", ", aDep_dirs.stream()
						.map(x -> String.format("%s", x)) // TODO _lib ??
						.collect(Collectors.toList()));
				root_file.accept(String.format("%s_bin = executable('%s', link_with: [ %s ], install: true)", project_name, project_name, deps_names)); // dependencies, include_directories
			} finally {
				((FileCharSink) root_file).close();
			}
		});
	}

	@Override
	public void run(final CR_State aSt, final CB_Output aOutput) throws Exception {
		write_makefiles();
	}

	private void write_makefiles() {
		Multimap<CompilerInstructions, String> lsp_outputs = writePipeline.st.lsp_outputs; // TODO move this
		write_makefiles_consumer().accept(lsp_outputs);

		//write_makefiles_latch.notify(lsp_outputs);
		write_makefiles_latch.notifyLatch(true);
	}

	private CP_Path getPath2(final String aName) {
		var root = pa.getCompilation().paths().outputRoot();

		var child = root.child(aName);
		return child;
	}

	public @NotNull Consumer<Multimap<CompilerInstructions, String>> write_makefiles_consumer() {
		if (_wmc != null)
			return _wmc;

		_wmc = write_makefiles_latch::notifyData;

		return _wmc;
	}

	private void write_lsp(@NotNull Multimap<CompilerInstructions, String> lsp_outputs, CompilerInstructions compilerInstructions, String aSub_dir) throws IOException {
		if (true || false) {
			CP_Path path = getPath2(aSub_dir, "meson.build");
			path.getPathPromise().then(pp -> {
				final MesonFile mesonFile = new MesonFile(this, aSub_dir, lsp_outputs, compilerInstructions, path);

				@NotNull final EG_Statement stmt = mesonFile;

				mesonFile.getPath().getPathPromise().then(ppp -> {

					//final String         pathString = mesonFile.getPathString();
					final String pathString2 = ppp.toString();

					final EOT_OutputFile off = new EOT_OutputFile(List_of(), pathString2, EOT_OutputType.BUILD, stmt);
					c.getOutputTree().add(off);
				});

			});
		}
	}

	private void write_prelude() throws IOException {
		if (true || false) {
			final CP_Path ppath1 = c.paths().outputRoot().child("Prelude");
			final CP_Path ppath  = ppath1.child("meson.build");

			if (false) {
				ppath1.getPathPromise().then(pp -> {
					System.err.println("mkdirs 215 " + ppath1.toFile());
					System.err.println("mkdirs 215b " + pp.toFile());
					pp.toFile().mkdirs(); // README just in case -- but should be unnecessary at this point
				});
			}

			List<String> files = List_of("'Prelude.c'");

			final StringBuilder sb = new StringBuilder();

			sb.append(String.format("Prelude_sources = files(\n%s\n)", String_join("\n", files)));
			sb.append("\n");
			sb.append("Prelude = static_library('Prelude', Prelude_sources, install: false,)"); // include_directories, dependencies: [],
			sb.append("\n");
			sb.append("\n");
			sb.append(String.format("%s_dep = declare_dependency( link_with: %s )", "Prelude", "Prelude")); // include_directories
			sb.append("\n");

			@NotNull final EG_Statement stmt = EG_Statement.of(sb.toString(), EX_Explanation.withMessage("WriteMesonPipeline"));
			final String                s    = ppath.toString();
			final EOT_OutputFile        off  = new EOT_OutputFile(List_of(), s, EOT_OutputType.BUILD, stmt);
			c.getOutputTree().add(off);
		}

	}

	private CP_Path getPath2(final String aName, final String aName2) {
		var root = pa.getCompilation().paths().outputRoot();

		var child = root.child(aName).child(aName2);
		return child;
	}
}

//
//
//

package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CD_FindStdLib;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.queries.QuerySourceFileParser;
import tripleo.elijah.nextgen.query.Mode;
import tripleo.elijah.util.Operation;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;

public class CD_FindStdLibImpl implements CD_FindStdLib {
	@Override
	public void findStdLib(final @NotNull CR_State crState,
						   final @NotNull String aPreludeName,
						   final @NotNull Consumer<Operation<CompilerInstructions>> coci) {
		try {
			final CompilationRunner compilationRunner = crState.runner();

			@NotNull final Operation<CompilerInstructions> oci = _____findStdLib(aPreludeName, compilationRunner._accessCompilation().getCompilationClosure(), compilationRunner);
			coci.accept(oci);
		} catch (Exception aE) {
			throw new RuntimeException(aE);
		}
	}

	public @NotNull Operation<CompilerInstructions> _____findStdLib(final @NotNull String prelude_name,
																	final @NotNull CompilationClosure cc,
																	final @NotNull CompilationRunner cr) {

		var slr = cc.getCompilation().paths().stdlibRoot();
		var pl  = slr.child("lib-" + prelude_name);
		var sle = pl.child("stdlib.ez");

		var local_stdlib_1 = sle.toFile();
		System.err.println("3939 "+local_stdlib_1);

		// TODO stdlib path here
		final File local_stdlib = new File("lib_elijjah/lib-" + prelude_name + "/stdlib.ez");

		Operation<CompilerInstructions> oci = null;
		if (local_stdlib.exists()) {
			try {
				final String name = local_stdlib.getName();

				// TODO really want EIT_Input or CK_SourceFile here 07/01
				final SourceFileParserParams p    = new SourceFileParserParams(null, local_stdlib, name, cc);
				final QuerySourceFileParser  qsfp = new QuerySourceFileParser(cr);
				oci = qsfp.process(p);

				if (oci.mode() == Mode.SUCCESS) {
					cc.getCompilation().pushItem(oci.success());
					return oci;
				}
			} catch (final Exception e) {
				return Operation.failure(e);
			}
		}

		assert oci != null;

		if (oci.mode() != Mode.FAILURE) {
			throw new IllegalStateException("expecting failure mode here.");
		}

		return Objects.requireNonNull(oci);
		//return Operation.failure(new Exception() {
		//	public String message() {
		//		return "No stdlib found";
		//	}
		//});
	}
}

package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation;

public record EIT_Input_HashSourceFile_Triple(String hash,
											  EIT_SourceOrigin source,
											  String filename) implements EIT_Input {
	public static @NotNull EIT_Input_HashSourceFile_Triple decode(final @NotNull String fn) {
		// move to Builder...Operation...
		// also CP_Filename hashPromise products
		final @NotNull Operation<String> op2 = Helpers.getHashForFilename(fn);

		if (op2.mode() == Mode.SUCCESS) {
			final String hh = op2.success();
			assert hh != null;

			EIT_SourceOrigin x;

			// TODO EG_Statement here

			if (fn.equals("lib_elijjah/lib-c/Prelude.elijjah")) {
				x = EIT_SourceOrigin.PREL;
			} else if (fn.startsWith("lib_elijjah/")) {
				x = EIT_SourceOrigin.LIB;
			} else if (fn.startsWith("test/")) {
				x = EIT_SourceOrigin.SRC;
			} else {
				throw new IllegalStateException("Error"); // Operation??
			}

			//noinspection UnnecessaryLocalVariable
			final EIT_Input_HashSourceFile_Triple yy2 = new EIT_Input_HashSourceFile_Triple(hh, x, fn);
			return yy2;
		}
		throw new IllegalStateException("hash failure"); // Operation??
	}

	@Override
	public @NotNull EIT_InputType getType() {
		// builder?? memtc st pat
		if (filename.endsWith(".elijah")) {
			return EIT_InputType.ELIJAH_SOURCE;
		}
		if (filename.endsWith(".ez")) {
			return EIT_InputType.EZ_FILE;
		}
		throw new IllegalStateException("Unexpected value " + filename);
	}
}

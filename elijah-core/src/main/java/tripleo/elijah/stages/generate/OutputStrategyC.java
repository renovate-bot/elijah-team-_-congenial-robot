/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.generate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;

import java.io.File;

/**
 * Created 1/13/21 5:54 AM
 */
public class OutputStrategyC {
	private final OutputStrategy outputStrategy;

	public OutputStrategyC(OutputStrategy outputStrategy) {
		this.outputStrategy = outputStrategy;
	}

	@Contract(pure = true)
	public @NotNull String Extension0(Old_GenerateResult.@NotNull TY aTy) {
		switch (aTy) {
		case IMPL:
			return (".c");
		case PRIVATE_HEADER:
			return ("_Priv.h");
		case HEADER:
			return (".h");
		default:
			throw new IllegalStateException("Unexpected value: " + aTy);
		}
	}

	public @NotNull EOT_OutputFile.FileNameProvider nameForFunction1(final @NotNull EvaFunction aGf, final GenerateResult.@NotNull TY aTy) {
		return new OSC_NFF(nameForFunction(aGf, aTy));
	}

	@Contract(pure = true)
	public String nameForFunction(@NotNull EvaFunction generatedFunction, Old_GenerateResult.@NotNull TY aTy) {
		EvaNode c = generatedFunction.getGenClass();
		if (c == null) {
			assert false;
			c = generatedFunction.getParent(); // TODO fixme
		}
		if (c instanceof EvaClass)
			return nameForClass((EvaClass) c, aTy);
		else if (c instanceof EvaNamespace)
			return nameForNamespace((EvaNamespace) c, aTy);
		assert false;
		return null;
	}

	public String nameForClass(final @NotNull EvaClass aX, final GenerateResult.@NotNull TY aTy) {
		return nameForClass1(aX, aTy).getFilename();
	}

	public String nameForNamespace(@NotNull EvaNamespace generatedNamespace, Old_GenerateResult.@NotNull TY aTy) {
		if (generatedNamespace.module().isPrelude()) {
			// We are dealing with the Prelude
			var          lsp0       = ("/Prelude/");
			var          name0      = ("Prelude");
			var          filename   = "";
			final String extension0 = Extension0(aTy);
			return new OFC_NFN(lsp0, name0, filename, extension0).getFilename();
		}
		String filename;
		if (generatedNamespace.getNamespaceStatement().getKind() == NamespaceTypes.MODULE) {
			final String moduleFileName = generatedNamespace.module().getFileName();
			File         moduleFile     = new File(moduleFileName);
			filename = moduleFile.getName();
			filename = strip_elijah_extension(filename);

			if (generatedNamespace.getCode() != 0) {
				filename = "elns-%d-%s".formatted(generatedNamespace.getCode(), filename); // hoohoo
			}

		} else {
			filename = generatedNamespace.getName();
		}

		final String lsp0 = n_lsp(generatedNamespace);

		OS_Package   pkg = generatedNamespace.getNamespaceStatement().getPackageName();
		final String name0;
		name0 = n_pkg(generatedNamespace, pkg);

		final String extension0 = Extension0(aTy);

		return new OFC_NFN(lsp0, name0, filename, extension0).getFilename();
	}

	public @NotNull OSC_NFC nameForClass1(@NotNull EvaClass aEvaClass, GenerateResult.@NotNull TY aTy) {
		if (aEvaClass.module().isPrelude()) {
			// We are dealing with the Prelude
/*
			StringBuilder sb = new StringBuilder();
			sb.append("/Prelude/");
			sb.append("Prelude");
			appendExtension(aTy, sb);
*/
			//return sb.toString();

			return new OSC_NFC("Prelude", "Prelude", "", Extension0(aTy));
		}

		final String lsp0, dir0, name0, extension0;

		final LibraryStatementPart lsp = aEvaClass.module().getLsp();
		lsp0 = a_lsp(lsp);

		dir0       = a_pkg(aEvaClass);
		name0      = a_name(aEvaClass, lsp);
		extension0 = Extension0(aTy);

		return new OSC_NFC(lsp0, dir0, name0, extension0);
	}

	@NotNull String strip_elijah_extension(@NotNull String aFilename) {
		if (aFilename.endsWith(".elijah")) {
			aFilename = aFilename.substring(0, aFilename.length() - 7);
		} else if (aFilename.endsWith(".elijjah")) {
			aFilename = aFilename.substring(0, aFilename.length() - 8);
		}
		return aFilename;
	}

	public class OSC_NFCo implements EOT_OutputFile.FileNameProvider {
		private final String s;

		public OSC_NFCo(final String aS) {
			s = aS;
		}

		@Override
		public String getFilename() {
			return s;
		}
	}

	public class OSC_NFF implements EOT_OutputFile.FileNameProvider {
		private final String s;

		public OSC_NFF(final String aS) {
			s = aS;
		}

		@Override
		public String getFilename() {
			return s;
		}

	}

	private static String n_lsp(final @NotNull EvaNamespace generatedNamespace) {
		final String               lsp0;
		final LibraryStatementPart lsp = generatedNamespace.module().getLsp();
		if (lsp == null) {
			//sb.append("___________________");
			lsp0 = "";
		} else {
			lsp0 = lsp.getInstructions().getName();
		}
		return lsp0;
	}

	private String n_pkg(final @NotNull EvaNamespace generatedNamespace, @Nullable OS_Package pkg) {
		final String name0;
		if (pkg != OS_Package.default_package) {
			if (pkg == null)
				pkg = findPackage(generatedNamespace.getNamespaceStatement());
			name0 = pkg.getName();
		} else {
			name0 = "#default_package-349";
		}
		return name0;
	}

	@Contract(pure = true)
	private static String a_lsp(final @Nullable LibraryStatementPart lsp) {
		final String lsp0;
		if (lsp == null) {
			//sb.append("______________");
			lsp0 = "";
		} else {
//			sb.append(generatedClass.module.lsp.getName());
			lsp0 = lsp.getInstructions().getName();
		}
		return lsp0;
	}

	@Contract(pure = true)
	private @NotNull String a_pkg(final @NotNull EvaClass aEvaClass) {
		final OS_Package pkg0 = aEvaClass.getKlass().getPackageName();
		final OS_Package pkg;

		final String dir0;
		if (pkg0 != OS_Package.default_package) {
			if (pkg0 == null) {
				pkg = findPackage(aEvaClass.getKlass());
			} else {
				pkg = pkg0;
			}

			dir0 = (pkg.getName());
		} else {
			dir0 = "";//_default_package-255";
		}

		return dir0;
	}

	private @NotNull String a_name(final @NotNull EvaClass aEvaClass, final @NotNull LibraryStatementPart lsp) {
		String name0;

		switch (outputStrategy.per()) {
		case PER_CLASS -> {
			if (aEvaClass.isGeneric())
				name0 = (aEvaClass.getNumberedName());
			else
				name0 = (aEvaClass.getName());
		}
		case PER_MODULE -> {
			OS_Module mod = aEvaClass.module();
			File      f   = new File(mod.getFileName());
			String    ff  = f.getName();
			name0 = strip_elijah_extension(ff);
		}
		case PER_PACKAGE -> {
			final OS_Package pkg2 = aEvaClass.getKlass().getPackageName();
			String           pkgName;
			if (pkg2 != OS_Package.default_package) { // FIXME ??
				pkgName = "__default_package-224";
			} else {
				pkgName = pkg2.getName();
			}
			name0 = pkgName;
		}
		case PER_PROGRAM -> {
			CompilerInstructions xx = lsp.getInstructions();
			String               n  = xx.getName();
			name0 = n;
		}
		default -> throw new IllegalStateException("Unexpected value: " + outputStrategy.per());
		}
		return name0;
	}

	@Contract(pure = true)
	private @Nullable OS_Package findPackage(OS_Element ee) {
		var e = ee;
		while (e != null) {
			e = e.getParent();
			if (e.getContext().getParent() == e.getContext())
				e = null;
			else {
				@NotNull ElObjectType t = DecideElObjectType.getElObjectType(e);
				switch (t) {
				case NAMESPACE:
					if (((NamespaceStatement) e).getPackageName() != null)
						return ((NamespaceStatement) e).getPackageName();
					break;
				case CLASS:
					if (((ClassStatement) e).getPackageName() != null)
						return ((ClassStatement) e).getPackageName();
					break;
				case FUNCTION:
					continue;
				default:
					// datatype, enum, alias
					continue;
				}
			}
		}
		return null;
	}

	public EOT_OutputFile.FileNameProvider nameForNamespace1(final @NotNull EvaNamespace aX, final GenerateResult.@NotNull TY aTy) {
		return new OFC_NFN_(aX, aTy);
	}

	public @NotNull OSC_NFCo nameForConstructor1(final @NotNull EvaConstructor aGf, final GenerateResult.@NotNull TY aTy) {
		return new OSC_NFCo(nameForConstructor(aGf, aTy));
	}

	@Contract(pure = true)
	public @Nullable String nameForConstructor(@NotNull EvaConstructor aEvaConstructor, Old_GenerateResult.@NotNull TY aTy) {
		EvaNode c = aEvaConstructor.getGenClass();
		if (c == null) c = aEvaConstructor.getParent(); // TODO fixme
		if (c instanceof EvaClass)
			return nameForClass((EvaClass) c, aTy);
		else if (c instanceof EvaNamespace)
			return nameForNamespace((EvaNamespace) c, aTy);
		return null;
	}

	public class OFC_NFN implements EOT_OutputFile.FileNameProvider {
		private final @Nullable String s;
		private final @Nullable String lsp0;
		private final @Nullable String name0;
		private final @Nullable String filename;
		private final @Nullable String extension0;

		public OFC_NFN(final String aS) {
			s          = aS;
			lsp0       = null;
			name0      = null;
			filename   = null;
			extension0 = null;
		}

		public OFC_NFN(final String aLsp0, final String aName0, final String aFilename, final String aExtension0) {
			s          = null;
			lsp0       = aLsp0;
			name0      = aName0;
			filename   = aFilename;
			extension0 = aExtension0;
		}

		@Override
		public @NotNull String getFilename() {
			if (s == null) {
				final String sb = lsp0 +
						"/" +
						name0 +
						"/" +
						filename +
						extension0;
				return sb;
			}
			return s;
		}
	}

	public class OFC_NFN_ implements EOT_OutputFile.FileNameProvider {
		private final @Nullable String s;

		public OFC_NFN_(final EvaNamespace aX, final GenerateResult.TY aTy) {
			s = nameForNamespace(aX, aTy);
		}

		@Override
		public @NotNull String getFilename() {
			return s;
		}
	}

	public class OSC_NFC implements EOT_OutputFile.FileNameProvider {
		String lsp;
		String dir;
		String extension;
		String basename;

		public OSC_NFC(String string, String string2, String string3, String extension0) {
			lsp       = string;
			dir       = string2;
			basename  = string3;
			extension = extension0;
		}

		@Override
		public @NotNull String getFilename() {
			StringBuilder sb = new StringBuilder();
			sb.append("/");
			sb.append(lsp);
			sb.append("/");
			sb.append(dir);
			if (!(basename.equals(""))) {
				sb.append("/");
				sb.append(basename);
			}
			sb.append(extension);
			return sb.toString();
		}
	}
}

//
//
//

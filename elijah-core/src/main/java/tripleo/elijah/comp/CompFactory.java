package tripleo.elijah.comp;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.WorldModule;

import java.io.File;
import java.util.List;

public interface CompFactory {
	EIT_ModuleInput createModuleInput(OS_Module aModule);

	Qualident createQualident(List<String> sl);

	InputRequest createInputRequest(File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp);

	WorldModule createWorldModule(OS_Module aM);

	class InputRequest {
		private final File    _file;
		private final boolean _do_out;
		private final LibraryStatementPart    lsp;
		private       Operation2<WorldModule> op;

		public InputRequest(final File aFile, final boolean aDoOut, final @Nullable LibraryStatementPart aLsp) {
			_file   = aFile;
			_do_out = aDoOut;
			lsp     = aLsp;
		}

		public File file() {
			return _file;
		}

		public boolean do_out() {
			return _do_out;
		}

		public LibraryStatementPart lsp() {
			return lsp;
		}

		public void setOp(final Operation2<WorldModule> aOwm) {
			op = aOwm;
		}
	}
}

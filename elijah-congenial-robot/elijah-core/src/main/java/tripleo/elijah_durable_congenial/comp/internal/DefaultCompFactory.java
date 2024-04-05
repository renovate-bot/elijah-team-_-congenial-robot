package tripleo.elijah_durable_congenial.comp.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.world.impl.DefaultWorldModule;
import tripleo.elijah_durable_congenial.ci.LibraryStatementPart;
import tripleo.elijah_durable_congenial.comp.CompFactory;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.lang.i.Qualident;
import tripleo.elijah_durable_congenial.lang.impl.QualidentImpl;
import tripleo.elijah_durable_congenial.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_durable_congenial.util.Helpers;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

import java.io.File;
import java.util.List;

class DefaultCompFactory implements CompFactory {
	private final CompilationImpl compilation;

	public DefaultCompFactory(final CompilationImpl aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
		return new EIT_ModuleInput(aModule, compilation);
	}

	@Override
	public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
		Qualident R = new QualidentImpl();
		for (String s : sl) {
			R.append(Helpers.string_to_ident(s));
		}
		return R;
	}

	@Override
	public @NotNull InputRequest createInputRequest(final File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp) {
		return new InputRequest(aFile, aDo_out, aLsp);
	}

	@Override
	public @NotNull WorldModule createWorldModule(final OS_Module m) {
		CompilationEnclosure ce = compilation.getCompilationEnclosure();
		final WorldModule    R  = new DefaultWorldModule(m, ce);

		return R;
	}
}

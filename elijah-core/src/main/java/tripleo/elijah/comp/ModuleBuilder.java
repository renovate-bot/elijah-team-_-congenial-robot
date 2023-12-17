package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.i.CompilationFlow;
import tripleo.elijah.comp.impl.DefaultCompilationFlow;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.WorldModule;

public class ModuleBuilder {
	//		private final Compilation compilation;
	private final @NotNull OS_Module mod;
	private                boolean   _addToCompilation = false;
	private @Nullable      String    _fn               = null;

	public ModuleBuilder(@NotNull Compilation aCompilation) {
//			compilation = aCompilation;
		mod = new OS_ModuleImpl();
		mod.setParent(aCompilation);
	}

	public @NotNull ModuleBuilder addToCompilation() {
		_addToCompilation = true;
		return this;
	}

	public OS_Module build() {
		if (_addToCompilation) {
			if (_fn == null) throw new IllegalStateException("Filename not set in ModuleBuilder");
			mod.getCompilation().world().addModule(mod, _fn, mod.getCompilation());
			//mod.getCompilation().addModule(mod, _fn);
		}
		return mod;
	}

	public @NotNull ModuleBuilder setContext() {
		final ModuleContext mctx = new ModuleContext(mod);
		mod.setContext(mctx);
		return this;
	}

	public @NotNull ModuleBuilder withFileName(String aFn) {
		_fn = aFn;
		mod.setFileName(aFn);
		return this;
	}

	public @NotNull ModuleBuilder withPrelude(String aPrelude) {
		final Operation2<WorldModule>[] p = new Operation2[]{null};

		if (false) {
			final CompilationFlow.CF_FindPrelude cffp = new CompilationFlow.CF_FindPrelude((pp) -> p[0] = pp);
			final DefaultCompilationFlow         flow = new DefaultCompilationFlow();
			flow.add(cffp);

			flow.run((CompilationImpl) mod.getCompilation());
		} else {
			p[0] = mod.getCompilation().findPrelude(aPrelude);
		}

		assert p[0].mode() == Mode.SUCCESS;

		mod.setPrelude(p[0].success().module());

		return this;
	}
}

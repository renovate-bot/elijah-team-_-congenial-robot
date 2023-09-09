/*  -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.notation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNode;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GN_GenerateNodesIntoSink implements GN_Notable, CompilationEnclosure.ModuleListener {
	private final GN_GenerateNodesIntoSinkEnv env;

	@Contract(value = "_ -> new", pure = true)
	@SuppressWarnings("unused")
	public static @NotNull GN_Notable getFactoryEnv(GN_Env aEnv) {
		return new GN_GenerateNodesIntoSink((GN_GenerateNodesIntoSinkEnv) aEnv);
	}

	public GN_GenerateNodesIntoSink(final GN_GenerateNodesIntoSinkEnv aEnv) {
		env = aEnv;

		env.pa().getCompilationEnclosure().addModuleListener(this);
	}

	@Override
	public void run() {
		final WorkManager wm = new WorkManager();

		env.moduleList().getMods().stream().collect(Collectors.toList()).forEach(mod -> {
			run_one_mod(mod, wm);
		});

		wm.drain(); // TODO here??

		env.pa().getAccessBus().resolveGenerateResult(env.gr());
	}

	private void run_one_mod(final OS_Module mod, final WorkManager wm) {
		final String lang = env.getLang(mod);
		if (lang == null) return;
		final GM_GenerateModuleRequest gmr  = new GM_GenerateModuleRequest(this, mod, env);
		final GM_GenerateModule        gm   = new GM_GenerateModule(gmr);
		final GM_GenerateModuleResult  ggmr = gm.getModuleResult(wm, env.resultSink1());
		ggmr.doResult(wm);
	}

	public GN_GenerateNodesIntoSinkEnv _env() {
		return env;
	}

	@Override
	public void listen(final @NotNull WorldModule module) {
		var wm = new WorkManager();
		run_one_mod(module.module(), wm);
	}

	@Override
	public void close() {

	}

	public Supplier<List<ProcessedNode>> processedNodesSupplier() {
		return () -> _env().lgc();
	}
}

/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.stages.gen_fn_c.GenFnC;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah.world.i.WorldModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created 5/16/21 12:35 AM
 */
public class GeneratePhase implements ReactiveDimension, CompilationEnclosure.ModuleListener {
	private @NotNull
	final PipelineLogic   pipelineLogic;
	private @NotNull
	final ElLog.Verbosity verbosity;
	private @NotNull
	final IPipelineAccess pa;

	private final @NotNull Map<OS_Module, GenerateFunctions> generateFunctions = new HashMap<OS_Module, GenerateFunctions>();
	private final @NotNull WorkManager                       wm                = new WorkManager();

	private @Nullable ICodeRegistrar codeRegistrar;

	public GeneratePhase(ElLog.Verbosity aVerbosity, final @NotNull IPipelineAccess aPa, PipelineLogic aPipelineLogic) {
		verbosity     = aVerbosity;
		pipelineLogic = aPipelineLogic;
		pa = aPa;

		pa.getCompilationEnclosure().addReactiveDimension(this);

		pa.getCompilationEnclosure().addModuleListener(this);
	}

	@NotNull
	public GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod) {
		final GenerateFunctions Result;
		if (generateFunctions.containsKey(mod)) {
			Result = generateFunctions.get(mod);
		} else {
			final GenFnC bc = new GenFnC();
			bc.set(pa);
			bc.set(pipelineLogic);
			Result = new GenerateFunctions(mod, bc);
			generateFunctions.put(mod, Result);
		}
		return Result;
	}

	public ElLog.Verbosity getVerbosity() {
		return verbosity;
	}

	public ICodeRegistrar getCodeRegistrar() {
		return codeRegistrar;
	}

	public void setCodeRegistrar(ICodeRegistrar aCodeRegistrar) {
		codeRegistrar = aCodeRegistrar;
	}

	@SuppressWarnings("LombokGetterMayBeUsed")
	public WorkManager getWm() {
		return wm;
	}

	@Override
	public void listen(final @NotNull WorldModule module) {
		//final GenerateFunctions x = getGenerateFunctions(module.module());
	}

	@Override
	public void close() {

	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//

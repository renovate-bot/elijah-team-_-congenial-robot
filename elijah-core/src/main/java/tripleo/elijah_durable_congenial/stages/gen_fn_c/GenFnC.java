package tripleo.elijah_durable_congenial.stages.gen_fn_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.EvaPipeline;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_congenial.comp.i.IPipelineAccess;
import tripleo.elijah_durable_congenial.entrypoints.ArbitraryFunctionEntryPoint;
import tripleo.elijah_durable_congenial.entrypoints.EntryPoint;
import tripleo.elijah_durable_congenial.entrypoints.MainClassEntryPoint;
import tripleo.elijah_durable_congenial.pre_world.Mirror_ArbitraryFunctionEntryPoint;
import tripleo.elijah_durable_congenial.pre_world.Mirror_EntryPoint;
import tripleo.elijah_durable_congenial.pre_world.Mirror_MainClassEntryPoint;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_congenial.stages.gen_fn.GeneratePhase;
import tripleo.elijah_durable_congenial.stages.gen_fn.IClassGenerator;
import tripleo.elijah_durable_congenial.stages.inter.ModuleThing;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

public class GenFnC {
	private IPipelineAccess pa;
	private PipelineLogic   pipelineLogic;

	public void set(final IPipelineAccess aPa0) {
		this.pa = aPa0;
	}

	public void set(final PipelineLogic aPl) {
		pipelineLogic = aPl;
	}

	public void addLog(final ElLog aLOG) {
		pa.addLog(aLOG);
	}

	public void addEntryPoint(final Mirror_EntryPoint aMirrorEntryPoint, final IClassGenerator aDcg) {
		pa.getCompilationEnclosure().addEntryPoint(aMirrorEntryPoint, aDcg);
	}

	public void addEntryPoint(final EntryPoint aEntryPoint,
							  final ModuleThing aMt,
							  final IClassGenerator aDcg,
							  final GenerateFunctions gf) {
		addEntryPoint(getMirrorEntryPoint(aEntryPoint, aMt, gf), aDcg);
	}

	@NotNull
	private Mirror_EntryPoint getMirrorEntryPoint(final EntryPoint entryPoint, final ModuleThing mt, final GenerateFunctions gf) {
		final Mirror_EntryPoint m;
		if (entryPoint instanceof final @NotNull MainClassEntryPoint mcep) {
			m = new Mirror_MainClassEntryPoint(mcep, mt, gf);
		} else if (entryPoint instanceof final @NotNull ArbitraryFunctionEntryPoint afep) {
			m = new Mirror_ArbitraryFunctionEntryPoint(afep, mt, gf);
		} else {
			throw new IllegalStateException("unhandled");
		}
		return m;
	}

	public GeneratePhase getGeneratePhase() {
		return pipelineLogic.generatePhase;
	}

	public void addFunctionStatement(final EvaFunction aGf) {
		pa.addFunctionStatement(new EvaPipeline.FunctionStatement(aGf));
	}
}

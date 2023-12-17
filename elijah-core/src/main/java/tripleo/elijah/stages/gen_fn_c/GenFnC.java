package tripleo.elijah.stages.gen_fn_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.EvaPipeline;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.entrypoints.ArbitraryFunctionEntryPoint;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.pre_world.Mirror_ArbitraryFunctionEntryPoint;
import tripleo.elijah.pre_world.Mirror_EntryPoint;
import tripleo.elijah.pre_world.Mirror_MainClassEntryPoint;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.stages.gen_fn.GeneratePhase;
import tripleo.elijah.stages.gen_fn.IClassGenerator;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.stages.logging.ElLog;

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

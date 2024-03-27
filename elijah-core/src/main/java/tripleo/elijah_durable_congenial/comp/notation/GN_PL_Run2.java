package tripleo.elijah_durable_congenial.comp.notation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah.comp.notation.GN_Notable;
import tripleo.elijah.world.impl.DefaultWorldModule;
import tripleo.elijah_congenial_durable.deduce2.GeneratedClasses;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_fn.DefaultClassGenerator;
import tripleo.elijah_durable_congenial.stages.gen_fn.IClassGenerator;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_congenial.stages.inter.ModuleThing;
import tripleo.elijah_durable_congenial.world.i.WorldModule;

import java.util.function.Consumer;

public class GN_PL_Run2 implements GN_Notable {
	private final @NotNull WorldModule           mod;
	private final          PipelineLogic         pipelineLogic;
	private final          CompilationEnclosure  ce;
	private final          DefaultClassGenerator dcg;
	private final          Consumer<WorldModule> worldConsumer;

	@Contract(pure = true)
	public GN_PL_Run2(final PipelineLogic aPipelineLogic,
					  final @NotNull WorldModule aMod,
					  final CompilationEnclosure aCe,
					  final Consumer<WorldModule> aWorldConsumer) {
		pipelineLogic = aPipelineLogic;
		mod           = aMod;
		ce            = aCe;
		worldConsumer = aWorldConsumer;

		dcg = new DefaultClassGenerator(pipelineLogic.dp);
	}

	@Contract("_ -> new")
	@SuppressWarnings("unused")
	public static @NotNull GN_PL_Run2 getFactoryEnv(GN_Env env1) {
		GN_PL_Run2_Env env = (GN_PL_Run2_Env) env1;
		return new GN_PL_Run2(env.pipelineLogic(), env.mod(), env.ce(), env.worldConsumer());
	}


	@Override
	public void run() {
		final DefaultWorldModule       worldModule = (DefaultWorldModule) mod;
		final GenerateFunctionsRequest rq          = new GenerateFunctionsRequest(dcg, worldModule);

		worldModule.setRq(rq);

		final Eventual<GeneratedClasses> plgc = pipelineLogic.handle(rq);
		plgc.register(pipelineLogic);

		plgc.then(lgc -> {
			final ICodeRegistrar cr              = dcg.getCodeRegistrar();
			final ResolvedNodes  resolved_nodes2 = new ResolvedNodes(cr, ce.getCompilation());

			resolved_nodes2.do_better(lgc, pipelineLogic, worldModule);

			worldConsumer.accept(worldModule);
		});
	}

	public record GenerateFunctionsRequest(IClassGenerator classGenerator,
										   DefaultWorldModule worldModule
	) {
		public ModuleThing mt() {
			return worldModule.thing();
		}

		public OS_Module mod() {
			return worldModule.module();
		}
	}
}

package tripleo.elijah.comp.notation;

import org.jdeferred2.Promise;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GN_PL_Run2 implements GN_Notable {
	private final @NotNull OS_Module             mod;
	private final          PipelineLogic         pipelineLogic;
	private final          CompilationEnclosure  ce;
	private final          DefaultClassGenerator dcg;
	private final          Consumer<WorldModule> worldConsumer;

	@Contract(pure = true)
	public GN_PL_Run2(final PipelineLogic aPipelineLogic,
					  final @NotNull OS_Module aMod,
					  final CompilationEnclosure aCe,
					  final Consumer<WorldModule> aWorldConsumer) {
		pipelineLogic = aPipelineLogic;
		mod           = aMod;
		ce            = aCe;
		worldConsumer = aWorldConsumer;

		dcg = new DefaultClassGenerator(pipelineLogic.dp);
	}

	public record GenerateFunctionsRequest(@NotNull List<EntryPoint> entryPoints,
										   IClassGenerator classGenerator,
										   DefaultWorldModule worldModule
	) {
		public ModuleThing mt() {
			return worldModule.thing();
		}

		public OS_Module mod() {
			return worldModule.module();
		}
	}

	@Override
	public void run() {
		final DefaultWorldModule       worldModule = new DefaultWorldModule(mod, ce);
		final GenerateFunctionsRequest rq          = new GenerateFunctionsRequest(mod.entryPoints(), dcg, worldModule);

		worldModule.setRq(rq);

		final Promise<DeducePhase.GeneratedClasses, Void, Void> plgc = pipelineLogic.handle(rq);

		plgc.then(lgc -> {
			final List<EvaNode>  resolved_nodes = new ArrayList<EvaNode>();
			final ICodeRegistrar cr             = dcg.getCodeRegistrar();

			__processNodes(lgc, resolved_nodes, cr);
			__processResolvedNodes(resolved_nodes, cr);

			pipelineLogic.dp.deduceModule(mod, lgc, pipelineLogic.getVerbosity());

			worldConsumer.accept(worldModule);
		});
	}

	@Contract("_ -> new")
	@SuppressWarnings("unused")
	public static @NotNull GN_PL_Run2 getFactoryEnv(GN_Env env1) {
		var env = (GN_PL_Run2_Env) env1;
		return new GN_PL_Run2(env.pipelineLogic(), env.mod(), env.ce(), env.worldConsumer());
	}

	private void __processResolvedNodes(final @NotNull List<EvaNode> resolved_nodes, final ICodeRegistrar cr) {
		resolved_nodes.stream()
				.filter(evaNode -> evaNode instanceof GNCoded)
				.map(evaNode -> (GNCoded) evaNode)
				.filter(coded -> coded.getCode() == 0)
				.forEach(coded -> {
					System.err.println("-*-*- __processResolvedNodes [NOT CODED] " + coded);
					coded.register(cr);
				});
	}

	@SuppressWarnings("TypeMayBeWeakened")
	private void __processNodes(final DeducePhase.@NotNull GeneratedClasses lgc,
								final @NotNull List<EvaNode> resolved_nodes,
								final @NotNull ICodeRegistrar cr) {
		for (final EvaNode evaNode : lgc) {
			if (!(evaNode instanceof final @NotNull GNCoded coded)) {
				throw new IllegalStateException("node must be coded");
			}

			switch (coded.getRole()) {
			case FUNCTION -> {
				cr.registerFunction1((BaseEvaFunction) evaNode);
			}
			case CLASS -> {
				final EvaClass evaClass = (EvaClass) evaNode;

				//assert (evaClass.getCode() != 0);
				if (evaClass.getCode() == 0) {
					cr.registerClass1(evaClass);
				}

//					if (generatedClass.getCode() == 0)
//						generatedClass.setCode(mod.getCompilation().nextClassCode());
				for (EvaClass evaClass2 : evaClass.classMap.values()) {
					if (evaClass2.getCode() == 0) {
						//evaClass2.setCode(mod.getCompilation().nextClassCode());
						cr.registerClass1(evaClass2);
					}
				}
				for (EvaFunction generatedFunction : evaClass.functionMap.values()) {
					for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
						if (identTableEntry.isResolved()) {
							EvaNode node = identTableEntry.resolvedType();
							resolved_nodes.add(node);
						}
					}
				}
			}
			case NAMESPACE -> {
				final EvaNamespace evaNamespace = (EvaNamespace) evaNode;
				if (coded.getCode() == 0) {
					//coded.setCode(mod.getCompilation().nextClassCode());
					cr.registerNamespace(evaNamespace);
				}
				for (EvaClass evaClass3 : evaNamespace.classMap.values()) {
					if (evaClass3.getCode() == 0) {
						//evaClass.setCode(mod.getCompilation().nextClassCode());
						cr.registerClass1(evaClass3);
					}
				}
				for (EvaFunction generatedFunction : evaNamespace.functionMap.values()) {
					for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
						if (identTableEntry.isResolved()) {
							EvaNode node = identTableEntry.resolvedType();
							resolved_nodes.add(node);
						}
					}
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + coded.getRole());
			}
		}
	}
}

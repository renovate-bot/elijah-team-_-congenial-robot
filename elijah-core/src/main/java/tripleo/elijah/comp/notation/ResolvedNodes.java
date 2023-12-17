package tripleo.elijah.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.world.i.WorldModule;

import java.util.ArrayList;
import java.util.List;

class ResolvedNodes {
	final         List<EvaNode>  resolved_nodes = new ArrayList<EvaNode>();
	private final ICodeRegistrar cr;
	private final Compilation cc;

	public ResolvedNodes(final ICodeRegistrar aCr, final Compilation aCc) {
		cr = aCr;
		cc = aCc;
	}

	public void do_better(final DeducePhase.GeneratedClasses lgc, final @NotNull PipelineLogic pipelineLogic, final @NotNull WorldModule worldModule) {
		this.init(lgc);
		this.part2();
		this.part3(pipelineLogic, worldModule, lgc);
	}

	public void init(final DeducePhase.@NotNull GeneratedClasses c) {
		if (cc.reports().outputOn(Finally.Outs.Out_6262)) {
			System.err.println("2222 " + c);
		}

		for (final EvaNode evaNode : c) {
			if (!(evaNode instanceof final @NotNull GNCoded coded)) {
				throw new IllegalStateException("node must be coded");
			}

			switch (coded.getRole()) {
			case FUNCTION -> {
				cr.registerFunction1((BaseEvaFunction) evaNode);
			}
			case CLASS -> {
				final EvaClass evaClass = (EvaClass) evaNode;

				if (evaClass.getCode() == 0) {
					cr.registerClass1(evaClass);
				} else {
					// FIXME 09/10 enable this
					// complain
				}

				for (EvaClass evaClass2 : evaClass.classMap.values()) {
					if (evaClass2.getCode() == 0) {
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

	public void part2() {
		resolved_nodes.stream()
				.filter(evaNode -> evaNode instanceof GNCoded)
				.map(evaNode -> (GNCoded) evaNode)
				.filter(coded -> coded.getCode() == 0)
				.forEach(coded -> {
					System.err.println("-*-*- __processResolvedNodes [NOT CODED] " + coded);
					coded.register(cr);
				});
	}

	public void part3(final @NotNull PipelineLogic pipelineLogic, final @NotNull WorldModule mod, final DeducePhase.GeneratedClasses lgc) {
		final DeducePhase deducePhase = pipelineLogic.dp;

		deducePhase.deduceModule(new DeducePhase_deduceModule_Request(mod.module(), lgc, pipelineLogic.getVerbosity(), deducePhase));
	}
}

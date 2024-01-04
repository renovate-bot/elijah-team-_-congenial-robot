package tripleo.elijah.stages.gen_generic.pipeline_impl;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaContainerNC;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;

import java.util.Collection;

@Getter
public class ProcessedNodeImpl implements ProcessedNode {
	private final EvaNode evaNode;

	public ProcessedNodeImpl(final EvaNode aEvaNode) {
		evaNode = aEvaNode;
	}

	@Override
	public boolean isContainerNode() {
		return evaNode instanceof EvaContainerNC;
	}

	@Override
	public boolean matchModule(final OS_Module aMod) {
		return evaNode.module() == aMod;
	}

	@Override
	public void processClassMap(final @NotNull GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen) {
		final EvaContainerNC nc = (EvaContainerNC) evaNode;

		final @NotNull Collection<EvaNode> gn2 = GenerateFiles.classes_to_list_of_generated_nodes(nc.classMap.values());
		final GenerateResult               gr4 = ggc.generateCode(gn2, aFileGen);
		aFileGen.gr().additional(gr4);
		aFileGen.resultSink().additional(gr4);
	}

	@Override
	public int process(final OS_Module mod, final GenerateFiles ggc, final GenerateResultEnv fileGen) {
		var processedNode = ProcessedNodeImpl.this;

		if (!processedNode.matchModule(mod)) {
			return ProcessedNode.MODULE_MISMATCH;
		}

		if (processedNode.isContainerNode()) {
			processedNode.processContainer(ggc, fileGen);

			processedNode.processConstructors(ggc, fileGen);
			processedNode.processFunctions(ggc, fileGen);
			processedNode.processClassMap(ggc, fileGen);

			return ProcessedNode.OK;
		}

		//return ProcessedNode.OTHER; // ??
		return ProcessedNode.OK;
	}

	@Override
	public void processConstructors(final @NotNull GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen) {
		final EvaContainerNC nc = (EvaContainerNC) evaNode;

		if (nc instanceof final @NotNull EvaClass evaClass) {
			final @NotNull Collection<EvaNode> gn2 = GenerateFiles.constructors_to_list_of_generated_nodes(evaClass.constructors.values());
			GenerateResult                     gr3 = ggc.generateCode(gn2, aFileGen);

			aFileGen.gr().additional(gr3);
			aFileGen.resultSink().additional(gr3);
		}
/*
		if (nc instanceof final EvaNamespace evaNamespace) {
			final @NotNull Collection<EvaNode> gn2 = GenerateFiles.constructors_to_list_of_generated_nodes(evaNamespace.constructors.values());
			GenerateResult                     gr3 = ggc.generateCode(gn2, wm, aResultSink);
			gr.additional(gr3);
			aResultSink.additional(gr3);
		}
*/
	}

	@Override
	public void processFunctions(final @NotNull GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen) {
		final EvaContainerNC nc = (EvaContainerNC) evaNode;

		final @NotNull Collection<EvaNode> gn1 = GenerateFiles.functions_to_list_of_generated_nodes(nc.functionMap.values());
		GenerateResult                     gr2 = ggc.generateCode(gn1, aFileGen);
		aFileGen.gr().additional(gr2);
		aFileGen.resultSink().additional(gr2);
	}

	@Override
	public void processContainer(final GenerateFiles ggc,
								 final @NotNull GenerateResultEnv aFileGen) {
		final EvaContainerNC nc = (EvaContainerNC) evaNode;

		nc.generateCode(aFileGen, ggc);
	}

	public EvaNode getEvaNode() {
		// antilombok
		return evaNode;
	}
}

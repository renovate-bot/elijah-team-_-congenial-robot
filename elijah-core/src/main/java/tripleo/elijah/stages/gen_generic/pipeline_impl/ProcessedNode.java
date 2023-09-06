package tripleo.elijah.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;

import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;

public interface ProcessedNode {
	boolean isContainerNode();

	boolean matchModule(OS_Module aMod);

	void processClassMap(GenerateFiles ggc,
						 final @NotNull GenerateResultEnv aFileGen);

	void processConstructors(GenerateFiles ggc,
							 final @NotNull GenerateResultEnv aFileGen);

	void processFunctions(GenerateFiles ggc,
						  final @NotNull GenerateResultEnv aFileGen);

	void processContainer(GenerateFiles aGgc, GenerateResultEnv aFileGen);
}

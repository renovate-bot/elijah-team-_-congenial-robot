package tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;

import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;

public interface ProcessedNode {
	int OTHER           = 3;
	int OK              = 2;
	int MODULE_MISMATCH = 1;
	int ZERO            = 0;

	boolean isContainerNode();

	boolean matchModule(OS_Module aMod);

	void processConstructors(GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen);

	void processFunctions(GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen);

	void processContainer(GenerateFiles aGgc, GenerateResultEnv aFileGen);

	void processClassMap(GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen);

	int process(OS_Module aMod, GenerateFiles aGgc, GenerateResultEnv aFileGen);
}

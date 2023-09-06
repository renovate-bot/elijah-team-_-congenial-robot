package tripleo.elijah.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingNamespace;
import tripleo.util.buffer.Buffer;

import java.util.List;

public interface GenerateResultSink {
	void add(EvaNode node);

	void addClass_0(GarishClass aGarishClass, Buffer aImplBuffer, Buffer aHeaderBuffer);

	void addClass_1(GarishClass aGarishClass, GenerateResult aGenerateResult, GenerateC aGenerateC);

	void addFunction(BaseEvaFunction aGf, List<C2C_Result> aRs, GenerateFiles aGenerateFiles);

	void additional(GenerateResult aGenerateResult);

	void addNamespace_0(GarishNamespace aLivingNamespace, Buffer aImplBuffer, Buffer aHeaderBuffer);

	void addNamespace_1(GarishNamespace aGarishNamespace, GenerateResult aGenerateResult, final GenerateC aGenerateC);

	@Nullable LivingClass getLivingClassForEva(EvaClass aEvaClass);

	@Nullable LivingNamespace getLivingNamespaceForEva(EvaNamespace aEvaClass);
}

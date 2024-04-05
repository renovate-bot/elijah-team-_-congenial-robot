package tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.stages.garish.GarishClass;
import tripleo.elijah_durable_congenial.stages.garish.GarishNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.C2C_Result;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_congenial.pp.IPP_Function;
import tripleo.elijah_congenial.pp.PP_Constructor;
import tripleo.elijah_durable_congenial.world.i.LivingClass;
import tripleo.elijah_durable_congenial.world.i.LivingNamespace;
import tripleo.elijah_durable_congenial.stages.garish.GarishClass;
import tripleo.elijah_durable_congenial.stages.gen_c.C2C_Result;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.world.i.LivingClass;
import tripleo.util.buffer.Buffer;

import java.util.List;

public interface GenerateResultSink {
	void add(EvaNode node);

	void addClass_0(GarishClass aGarishClass, Buffer aImplBuffer, Buffer aHeaderBuffer);

	void addClass_1(GarishClass aGarishClass, GenerateResult aGenerateResult, GenerateC aGenerateC);

	void addFunction(IPP_Function aIPPFunction, List<C2C_Result> aRs, GenerateFiles aGenerateFiles);

	void additional(GenerateResult aGenerateResult);

	void addNamespace_0(GarishNamespace aLivingNamespace, Buffer aImplBuffer, Buffer aHeaderBuffer);

	void addNamespace_1(GarishNamespace aGarishNamespace, GenerateResult aGenerateResult, final GenerateC aGenerateC);

	@Nullable LivingClass getLivingClassForEva(EvaClass aEvaClass);

	@Nullable LivingNamespace getLivingNamespaceForEva(EvaNamespace aEvaClass);

	void addFunction(PP_Constructor aPPConstructor, List<C2C_Result> aRs, GenerateC aGenerateC);
}

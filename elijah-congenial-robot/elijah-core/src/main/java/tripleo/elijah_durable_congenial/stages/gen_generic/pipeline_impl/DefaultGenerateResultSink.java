package tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_congenial.pp.IPP_Function;
import tripleo.elijah_congenial.pp.PP_Constructor;
import tripleo.elijah_durable_congenial.comp.i.IPipelineAccess;
import tripleo.elijah_durable_congenial.nextgen.output.NG_OutputClass;
import tripleo.elijah_durable_congenial.nextgen.output.NG_OutputFunction;
import tripleo.elijah_durable_congenial.nextgen.output.NG_OutputNamespace;
import tripleo.elijah_durable_congenial.stages.garish.GarishClass;
import tripleo.elijah_durable_congenial.stages.garish.GarishNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.C2C_Result;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.world.i.LivingClass;
import tripleo.elijah_durable_congenial.world.i.LivingNamespace;
import tripleo.util.buffer.Buffer;

import java.util.List;

public class DefaultGenerateResultSink implements GenerateResultSink {
	private final @NotNull IPipelineAccess pa;

	@Contract(pure = true)
	public DefaultGenerateResultSink(final @NotNull IPipelineAccess pa0) {
		pa = pa0;
	}

	@Override
	public void add(final EvaNode node) {
		throw new IllegalStateException("Error");
	}

	@Override
	public void addClass_0(final GarishClass aGarishClass, final Buffer aImplBuffer, final Buffer aHeaderBuffer) {
		throw new IllegalStateException("Error");
	}

	@Override
	public void addClass_1(final @NotNull GarishClass aGarishClass,
						   final @NotNull GenerateResult gr,
						   final @NotNull GenerateC aGenerateC) {
		NG_OutputClass o = new NG_OutputClass();
		o.setClass(aGarishClass, aGenerateC);
		pa.addOutput(o);
	}

	@Override
	public void addFunction(final IPP_Function aGf, final List<C2C_Result> aRs, final GenerateFiles aGenerateFiles) {
		NG_OutputFunction o = new NG_OutputFunction();
		o.setFunction(aGf, aGenerateFiles, aRs);
		pa.addOutput(o);
	}

	@Override
	public void additional(final @NotNull GenerateResult aGenerateResult) {
		//throw new IllegalStateException("Error");
	}

	@Override
	public void addNamespace_0(final @NotNull GarishNamespace aGarishNamespace, final Buffer aImplBuffer, final Buffer aHeaderBuffer) {
		throw new IllegalStateException("Error");
	}

	@Override
	public void addNamespace_1(final @NotNull GarishNamespace aGarishNamespace,
							   final @NotNull GenerateResult gr,
							   final @NotNull GenerateC aGenerateC) {
		NG_OutputNamespace o = new NG_OutputNamespace();
		o.setNamespace(aGarishNamespace, aGenerateC);
		pa.addOutput(o);
	}

	@Override
	public LivingClass getLivingClassForEva(final EvaClass aEvaClass) {
		return pa.getCompilation().livingRepo().getClass(aEvaClass);
	}

	@Override
	public LivingNamespace getLivingNamespaceForEva(final EvaNamespace aEvaNamespace) {
		return pa.getCompilation().livingRepo().getNamespace(aEvaNamespace);
	}

	@Override
	public void addFunction(final PP_Constructor aPPConstructor, final List<C2C_Result> aRs, final GenerateC aGenerateC) {
//		throw new UnintendedUseException();
	}
}

package tripleo.elijah_congenial.pipelines;

import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.nextgen.output.NG_OutputClass;
import tripleo.elijah.nextgen.output.NG_OutputFunction;
import tripleo.elijah.nextgen.output.NG_OutputNamespace;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingNamespace;
import tripleo.elijah_congenial.pipelines.eva.EvaPipelineImpl_PipelineAccess;

public interface DGRS_Client {
	static DGRS_Client of(IPipelineAccess aPipelineAccess) {
		return new DGRS_Client() {
			@Override
			public void addOutput(final NG_OutputClass aOutputClass) {
				aPipelineAccess.addOutput(aOutputClass);
			}

			@Override
			public void addOutput(final NG_OutputFunction aOutputFunction) {
				aPipelineAccess.addOutput(aOutputFunction);
			}

			@Override
			public LivingClass getLivingClass(final EvaClass aEvaClass) {
				return aPipelineAccess.getCompilation().livingRepo().getClass(aEvaClass);
			}

			@Override
			public void addOutput(final NG_OutputNamespace aOutputNamespace) {
				aPipelineAccess.addOutput(aOutputNamespace);
			}

			@Override
			public LivingNamespace getLivingNamespace(final EvaNamespace aEvaNamespace) {
				return aPipelineAccess.getCompilation().livingRepo().getNamespace(aEvaNamespace);
			}
		};
	}

	static DGRS_Client of(EvaPipelineImpl_PipelineAccess aPipelineAccess) {
		return new DGRS_Client() {
			@Override
			public void addOutput(final NG_OutputClass aOutputClass) {
				aPipelineAccess._dgrs().addOutput(aOutputClass);
			}

			@Override
			public void addOutput(final NG_OutputFunction aOutputFunction) {
				aPipelineAccess._dgrs().addOutput(aOutputFunction);
			}

			@Override
			public LivingClass getLivingClass(final EvaClass aEvaClass) {
				return aPipelineAccess._dgrs().getCompilation().livingRepo().getClass(aEvaClass);
			}

			@Override
			public void addOutput(final NG_OutputNamespace aOutputNamespace) {
				aPipelineAccess._dgrs().addOutput(aOutputNamespace);
			}

			@Override
			public LivingNamespace getLivingNamespace(final EvaNamespace aEvaNamespace) {
				return aPipelineAccess._dgrs().getCompilation().livingRepo().getNamespace(aEvaNamespace);
			}
		};
	}

	void addOutput(NG_OutputClass aO);

	void addOutput(NG_OutputFunction aO);

	LivingClass getLivingClass(EvaClass aEvaClass);

	void addOutput(NG_OutputNamespace aO);

	LivingNamespace getLivingNamespace(EvaNamespace aEvaNamespace);
}

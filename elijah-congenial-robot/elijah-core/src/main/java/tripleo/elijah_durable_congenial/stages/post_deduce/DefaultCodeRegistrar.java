package tripleo.elijah_durable_congenial.stages.post_deduce;

import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_congenial.world.i.LivingRepo;

public class DefaultCodeRegistrar implements ICodeRegistrar {
	private final Compilation compilation;

	public DefaultCodeRegistrar(final Compilation aC) {
		compilation = aC;
	}

	@Override
	public void registerClass(final EvaClass aClass) {
		compilation.livingRepo().addClass(aClass, LivingRepo.Add.MAIN_CLASS);
	}

	@Override
	public void registerClass1(final EvaClass aClass) {
		compilation.livingRepo().addClass(aClass, LivingRepo.Add.NONE);
	}

	@Override
	public void registerFunction(final BaseEvaFunction aFunction) {
		compilation.livingRepo().addFunction(aFunction, LivingRepo.Add.MAIN_FUNCTION);
	}

	@Override
	public void registerFunction1(final BaseEvaFunction aFunction) {
		compilation.livingRepo().addFunction(aFunction, LivingRepo.Add.NONE);
	}

	@Override
	public void registerNamespace(final EvaNamespace aNamespace) {
		compilation.livingRepo().addNamespace(aNamespace, LivingRepo.Add.NONE);
	}
}

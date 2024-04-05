package tripleo.elijah_durable_congenial.world.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.functionality.f291.AmazingPart;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_congenial.world.i.LivingFunction;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;

public class DefaultLivingFunction implements LivingFunction {
	private final @NotNull FunctionDef     _element;
	private final @NotNull BaseEvaFunction _gf;

	public DefaultLivingFunction(final @NotNull BaseEvaFunction aFunction) {
		_element = aFunction.getFD();
		_gf      = aFunction;
	}

	@Override
	public int getCode() {
		return _gf.getCode();
	}

	@Override
	public FunctionDef getElement() {
		return _element;
	}

	@Override
	public void offer(final AmazingPart aAp) {
		aAp.reverseOffer(this);
	}

	@Override
	public BaseEvaFunction evaNode() {
		return _gf;
	}
}

package tripleo.elijah.world.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.functionality.f291.AmazingPart;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.world.i.LivingFunction;

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

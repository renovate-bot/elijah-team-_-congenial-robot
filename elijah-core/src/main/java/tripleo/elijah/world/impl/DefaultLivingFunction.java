package tripleo.elijah.world.impl;

import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.comp.functionality.f291.AmazingPart;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah.stages.gen_fn.IEvaFunctionBase;
import tripleo.elijah.world.i.LF_CodeRegistration;
import tripleo.elijah.world.i.LivingFunction;

public class DefaultLivingFunction implements LivingFunction {
	private final @NotNull FunctionDef     _element;
	private final @NotNull BaseEvaFunction _gf;
	private Eventual<Integer>               codeCallback;
	private boolean __registered;

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

	@Override
	public void codeRegistration(final LF_CodeRegistration acr) {
		if (codeCallback == null) {
			// 1. allocate
			codeCallback = new Eventual<>();

			// 2. initialize
			final Compilation compilation = _element.getContext().module().getCompilation();
			final FluffyComp  fluffy      = compilation.getFluffy();
			codeCallback.register(fluffy);

			// 3. setup
			codeCallback.then(i -> {
				final IEvaFunctionBase evaFunction = evaNode();
				evaFunction.setCode(i);
				__registered = true;
			});
		}

		// 4. trigger
		if (evaNode().getCode() == 0) {
			final EvaFunction evaFunction = (EvaFunction) evaNode();
			acr.accept(evaFunction, codeCallback);
		}
	}

	@Override
	public boolean isRegistered() {
		return __registered;
	}

	@Override
	public void listenRegister(final DoneCallback<Integer> aCodeCallback) {
		codeCallback.then(aCodeCallback);
	}
}

package tripleo.elijah_durable_congenial.world.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.comp.functionality.f291.AmazingPart;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.stages.garish.GarishClass;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_congenial.world.i.LivingClass;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.world.i.LivingClass;

public class DefaultLivingClass implements LivingClass {
	private final           ClassStatement _element;
	private final @Nullable EvaClass       _gc;
	private @Nullable       GarishClass    _garish;

	@Contract(pure = true)
	public DefaultLivingClass(final ClassStatement aElement) {
		_element = aElement;
		_gc      = null;
		_garish  = null;
	}

	public DefaultLivingClass(final @NotNull EvaClass aClass) {
		_element = aClass.getKlass();
		_gc      = aClass;
		_garish  = null;
	}

	@Override
	public EvaClass evaNode() {
		return _gc;
	}

	@Override
	public ClassStatement getElement() {
		return _element;
	}

	@Override
	public int getCode() {
		return _gc.getCode();
	}

	public EvaClass gc() {
		return _gc;
	}

	@Override
	@Contract(mutates = "this")
	public @NotNull GarishClass getGarish() {
		if (_garish == null) {
			_garish = new GarishClass(this);
		}

		return _garish;
	}

	@Override
	public void offer(final AmazingPart amazingPart) {
		amazingPart.reverseOffer(this);
	}

	//@Override
	//public void setGarish(final GarishClass aGarishClass) {
	//	_garish = aGarishClass;
	//}
}

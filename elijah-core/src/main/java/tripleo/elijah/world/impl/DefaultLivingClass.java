package tripleo.elijah.world.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.world.i.LivingClass;

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

	//@Override
	//public void setGarish(final GarishClass aGarishClass) {
	//	_garish = aGarishClass;
	//}
}

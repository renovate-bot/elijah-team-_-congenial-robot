package tripleo.elijah.stages.gen_fn;

// T is either a OS_Element or IExpression
public final class EvaExpression<T> {
	private final T              _t;
	private final BaseTableEntry _e;

	public EvaExpression(final T aT, final BaseTableEntry aE) {
		_t = aT;
		_e = aE;
	}

	public T get() {
		return _t;
	}

	public BaseTableEntry getEntry() {
		return _e;
	}
}

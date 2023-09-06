package tripleo.elijah.stages.deduce.fluffy.i;

public interface FluffyVarTarget {
	Ty getTy();

	/**
	 * MEMBER means class or namespace<br/>
	 * FUNCTION means a function or something "under" it (loop, etc)<br/>
	 * <br/>
	 * ARGUMENT means a function argument (not used...)
	 */
	enum Ty {FUNCTION, MEMBER}

}

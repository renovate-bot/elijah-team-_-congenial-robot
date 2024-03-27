package tripleo.elijah_durable_congenial.stages.deduce;

/**
 * Marker interface to show intent
 * <p>
 * Created 6/8/21 2:29 AM
 */
public interface IInvocation {
	void setForFunctionInvocation(FunctionInvocation aFunctionInvocation);
	String asString();
}

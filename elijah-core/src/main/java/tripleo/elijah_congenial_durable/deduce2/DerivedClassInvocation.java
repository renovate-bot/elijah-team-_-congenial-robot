package tripleo.elijah_congenial_durable.deduce2;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;

import java.util.function.Supplier;

/**
 * Created 1/5/22 11:27 PM
 */
public class DerivedClassInvocation extends ClassInvocation {
	private final ClassInvocation derivation;

	public DerivedClassInvocation(final @NotNull ClassStatement aClassStatement, final ClassInvocation aClassInvocation, final Supplier<DeduceTypes2> aDeduceTypes2) {
		super(aClassStatement, null, aDeduceTypes2);
		derivation = aClassInvocation;
	}

	public ClassInvocation getDerivation() {
		return derivation;
	}

	@Override
	public void setForFunctionInvocation(final @NotNull FunctionInvocation aFunctionInvocation) {
		aFunctionInvocation.setClassInvocation(this);
	}
}

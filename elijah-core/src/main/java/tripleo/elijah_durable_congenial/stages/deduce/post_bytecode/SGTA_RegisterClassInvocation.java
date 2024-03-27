package tripleo.elijah_durable_congenial.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.deduce.NULL_DeduceTypes2;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.DeducePhase;
import tripleo.elijah_durable_congenial.stages.gen_fn.GenType;

public class SGTA_RegisterClassInvocation implements setup_GenType_Action {

	private final ClassStatement classStatement;
	private final DeducePhase    phase;

	public SGTA_RegisterClassInvocation(final ClassStatement aClassStatement, final DeducePhase aPhase) {
		classStatement = aClassStatement;
		phase          = aPhase;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		//@Nullable ClassInvocation ci = _inj().new_ClassInvocation(classStatement, null);
		@Nullable ClassInvocation ci = new ClassInvocation(classStatement, null, new NULL_DeduceTypes2()); // !! 08/28
		ci = phase.registerClassInvocation(ci);

		arena.put("ci", ci);
	}
}

package tripleo.elijah_durable_congenial.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.lang.i.ClassStatement;
import tripleo.elijah_durable_congenial.lang.i.Context;

class DeduceCentral {
	private final DeduceTypes2 deduceTypes2;
	private DT_Env myEnv;

	public DeduceCentral(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	public DeduceTypes2 getDeduceTypes2() {
		return deduceTypes2;
	}

	public @NotNull DC_ClassNote note_Class(final ClassStatement aE, final Context aCtx) {
		DC_ClassNote cn = deduceTypes2._inj().new_DC_ClassNote(aE, aCtx, this);
		return cn;
	}

	public DT_Env getEnv() {
		if (myEnv == null) {
			myEnv = deduceTypes2._inj().new_DT_Env(deduceTypes2.LOG, deduceTypes2._errSink(), this);
		}
		return myEnv;
	}
}

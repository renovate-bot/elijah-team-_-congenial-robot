package tripleo.elijah.nextgen.rosetta;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2_deduceFunctions_Request;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;

public class Rosetta {

	@Contract("_ -> new")
	public static @NotNull DeduceTypes2 create(final DeduceTypes2Request aDeduceTypes2Request) {
		return new DeduceTypes2(aDeduceTypes2Request);
	}

	public static void create_call(final @NotNull DeduceTypes2_deduceFunctions_Request rq) {
		final DeducePhase deducePhase = rq.getDeducePhase();

		deducePhase.__DeduceTypes2_deduceFunctions_Request__run(rq.getB(), rq.getRequest());
	}
}

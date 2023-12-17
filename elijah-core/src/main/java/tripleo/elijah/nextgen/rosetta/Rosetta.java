package tripleo.elijah.nextgen.rosetta;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request_TWO;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2_deduceFunctions_Request;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.deduce.DeduceTypes2_TWO;
import tripleo.elijah.stages.deduce_r.RegisterClassInvocation_resp;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.GenerateFunctions2;
import tripleo.elijah.stages.gen_fn_r.GenerateEvaClassRequest;
import tripleo.elijah.stages.gen_fn_r.GenerateEvaClassResponse;
import tripleo.elijah.stages.gen_fn_r.RegisterClassInvocation_env;

@SuppressWarnings({"UtilityClassCanBeEnum", "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass"})
public class Rosetta {
	private Rosetta() { }

	@Contract("_ -> new")
	public static @NotNull DeduceTypes2 create(final DeduceTypes2Request aDeduceTypes2Request) {
		return new DeduceTypes2(aDeduceTypes2Request);
	}

	public static DeduceTypes2_TWO create(final DeduceTypes2Request aDeduceTypes2Request, final DeduceTypes2Request_TWO aTWO) {
		return new DeduceTypes2_TWO(aDeduceTypes2Request);
	}

	public static void create_call(final @NotNull DeduceTypes2_deduceFunctions_Request rq) {
		final DeducePhase deducePhase = rq.getDeducePhase();

		deducePhase.__DeduceTypes2_deduceFunctions_Request__run(rq.getB(), rq.getRequest());
	}

	public static RCIE create(final RegisterClassInvocation_env aEnv, final RegisterClassInvocation_resp aResp) {
		return new RCIE(aEnv, aResp);
	}

	@SuppressWarnings("FinalClass")
	public static final class RCIE implements RosettaApplyable {
		private final RegisterClassInvocation_env env;
		private final RegisterClassInvocation_resp resp;

		public RCIE(final RegisterClassInvocation_env aEnv, final RegisterClassInvocation_resp aResp) {
			env = aEnv;
			resp = aResp;
		}

		@Override
		public void apply() {
			final @NotNull ClassInvocation ci2 = env.phase().registerClassInvocation(env.ci());
			resp.succeed(ci2);
		}
	}

	@SuppressWarnings("FinalClass")
	public static final class GECR implements RosettaApplyable {
		private final GenerateEvaClassRequest rq;
		private final GenerateEvaClassResponse rsp;

		public GECR(final GenerateEvaClassRequest aRq, final GenerateEvaClassResponse aRsp) {
			rq = aRq;
			rsp = aRsp;
		}

		@Override
		public void apply() {
			@NotNull EvaClass kl = new GenerateFunctions2(rq.getGenerateFunctions()).generateClass(rq.getClassStatement(), rq.getClassInvocation(), rq.getPassthruEnv());
			rsp.getEvaClassPromise().resolve(kl);
		}
	}

	@Contract(pure = true)
	public static @NotNull GECR create(final GenerateEvaClassRequest aRq, final GenerateEvaClassResponse aRsp) {
		GECR gecr = new GECR(aRq, aRsp);
		return gecr;
	}
}

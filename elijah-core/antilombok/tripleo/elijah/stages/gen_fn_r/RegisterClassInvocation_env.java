package tripleo.elijah.stages.gen_fn_r;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.sanaa.ElIntrinsics;

public final class RegisterClassInvocation_env {
	
	private final @NotNull  ClassInvocation ci;
	private final @Nullable DeduceTypes2 deduceTypes2;
	private final @Nullable DeducePhase phase;

	public RegisterClassInvocation_env(
			@NotNull ClassInvocation ci,
			@Nullable DeduceTypes2 deduceTypes2,
			@Nullable DeducePhase phase) {
		ElIntrinsics.checkNotNullParameter(ci, "ci");
		
		this.ci = ci;
		this.deduceTypes2 = deduceTypes2;
		this.phase = phase;
	}

	public RegisterClassInvocation_env(
			@NotNull ClassStatement classStatement, 
			@NotNull DeduceTypes2 deduceTypes2,
			@NotNull DeducePhase phase) {
		ElIntrinsics.checkNotNullParameter(classStatement, "classStatement");
		ElIntrinsics.checkNotNullParameter(deduceTypes2, "deduceTypes2");
		ElIntrinsics.checkNotNullParameter(phase, "phase");

		ClassInvocation var10001 = phase.registerClassInvocation(classStatement, deduceTypes2);
		ElIntrinsics.checkNotNullExpressionValue(var10001, "registerClassInvocation(...)");

		this.ci = var10001;
		this.deduceTypes2 = deduceTypes2;
		this.phase = phase;
	}

	@NotNull
	public final ClassInvocation ci() {
		return this.ci;
	}

	@NotNull
	public final DeduceTypes2 deduceTypes2() {
		DeduceTypes2 var10000 = this.deduceTypes2;
		ElIntrinsics.checkNotNull(var10000);
		return var10000;
	}

	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof RegisterClassInvocation_env)) {
			return false;
		} else {
			RegisterClassInvocation_env var2 = (RegisterClassInvocation_env) other;
			if (!ElIntrinsics.areEqual(this.ci, var2.ci)) {
				return false;
			} else if (!ElIntrinsics.areEqual(this.deduceTypes2, var2.deduceTypes2)) {
				return false;
			} else {
				return ElIntrinsics.areEqual(this.phase, var2.phase);
			}
		}
	}

	@NotNull
	public final ClassInvocation getCi() {
		return this.ci;
	}

	@Nullable
	public final DeduceTypes2 getDeduceTypes2() {
		return this.deduceTypes2;
	}

	@Nullable
	public final DeducePhase getPhase() {
		return this.phase;
	}

	public int hashCode() {
		int result = this.ci.hashCode();
		result = result * 31 + (this.deduceTypes2 == null ? 0 : this.deduceTypes2.hashCode());
		result = result * 31 + (this.phase == null ? 0 : this.phase.hashCode());
		return result;
	}

	@NotNull
	public final DeducePhase phase() {
		DeducePhase var10000 = this.phase;
		ElIntrinsics.checkNotNull(var10000);
		return var10000;
	}

	@NotNull
	public String toString() {
		return "RegisterClassInvocation_env(ci=" + this.ci + ", deduceTypes2=" + this.deduceTypes2 + ", phase="
				+ this.phase + ')';
	}
}

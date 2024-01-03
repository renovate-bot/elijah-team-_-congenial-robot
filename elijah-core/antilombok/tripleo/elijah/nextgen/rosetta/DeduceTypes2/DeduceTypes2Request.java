package tripleo.elijah.nextgen.rosetta.DeduceTypes2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.u.ElIntrinsics;

public final class DeduceTypes2Request {
   @NotNull
   private final OS_Module module;
   @NotNull
   private final DeducePhase deducePhase;
   @NotNull
   private final ElLog.Verbosity verbosity;

   public DeduceTypes2Request(@NotNull OS_Module module, @NotNull DeducePhase deducePhase, @NotNull ElLog.Verbosity verbosity) {
      ElIntrinsics.checkNotNullParameter(module, "module");
      ElIntrinsics.checkNotNullParameter(deducePhase, "deducePhase");
      ElIntrinsics.checkNotNullParameter(verbosity, "verbosity");

      this.module = module;
      this.deducePhase = deducePhase;
      this.verbosity = verbosity;
   }

   @NotNull
   public final OS_Module getModule() {
      return this.module;
   }

   @NotNull
   public final DeducePhase getDeducePhase() {
      return this.deducePhase;
   }

   @NotNull
   public final ElLog.Verbosity getVerbosity() {
      return this.verbosity;
   }

   @NotNull
   public final OS_Module component1() {
      return this.module;
   }

   @NotNull
   public final DeducePhase component2() {
      return this.deducePhase;
   }

   @NotNull
   public final ElLog.Verbosity component3() {
      return this.verbosity;
   }

   @NotNull
   public final DeduceTypes2Request copy(@NotNull OS_Module module, @NotNull DeducePhase deducePhase, @NotNull ElLog.Verbosity verbosity) {
      ElIntrinsics.checkNotNullParameter(module, "module");
      ElIntrinsics.checkNotNullParameter(deducePhase, "deducePhase");
      ElIntrinsics.checkNotNullParameter(verbosity, "verbosity");
      return new DeduceTypes2Request(module, deducePhase, verbosity);
   }

   // $FF: synthetic method
   public static DeduceTypes2Request copy$default(DeduceTypes2Request var0, OS_Module var1, DeducePhase var2, ElLog.Verbosity var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.module;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.deducePhase;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.verbosity;
      }

      return var0.copy(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "DeduceTypes2Request(module=" + this.module + ", deducePhase=" + this.deducePhase + ", verbosity=" + this.verbosity + ")";
   }

   public int hashCode() {
      int result = this.module.hashCode();
      result = result * 31 + this.deducePhase.hashCode();
      result = result * 31 + this.verbosity.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DeduceTypes2Request)) {
         return false;
      } else {
         DeduceTypes2Request var2 = (DeduceTypes2Request)other;
         if (!ElIntrinsics.areEqual(this.module, var2.module)) {
            return false;
         } else if (!ElIntrinsics.areEqual(this.deducePhase, var2.deducePhase)) {
            return false;
         } else {
            return this.verbosity == var2.verbosity;
         }
      }
   }
}

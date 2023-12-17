package tripleo.elijah.nextgen.rosetta.DeduceTypes2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.logging.ElLog;

@Metadata(
   mv = {1, 9, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"},
   d2 = {"Ltripleo/elijah/nextgen/rosetta/DeduceTypes2/DeduceTypes2Request;", "", "module", "Ltripleo/elijah/lang/i/OS_Module;", "deducePhase", "Ltripleo/elijah/stages/deduce/DeducePhase;", "verbosity", "Ltripleo/elijah/stages/logging/ElLog$Verbosity;", "(Ltripleo/elijah/lang/i/OS_Module;Ltripleo/elijah/stages/deduce/DeducePhase;Ltripleo/elijah/stages/logging/ElLog$Verbosity;)V", "getDeducePhase", "()Ltripleo/elijah/stages/deduce/DeducePhase;", "getModule", "()Ltripleo/elijah/lang/i/OS_Module;", "getVerbosity", "()Ltripleo/elijah/stages/logging/ElLog$Verbosity;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "tripleo.elijah.core"}
)
public final class DeduceTypes2Request {
   @NotNull
   private final OS_Module module;
   @NotNull
   private final DeducePhase deducePhase;
   @NotNull
   private final ElLog.Verbosity verbosity;

   public DeduceTypes2Request(@NotNull OS_Module module, @NotNull DeducePhase deducePhase, @NotNull ElLog.Verbosity verbosity) {
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(deducePhase, "deducePhase");
      Intrinsics.checkNotNullParameter(verbosity, "verbosity");
//      super();
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
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(deducePhase, "deducePhase");
      Intrinsics.checkNotNullParameter(verbosity, "verbosity");
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
         if (!Intrinsics.areEqual(this.module, var2.module)) {
            return false;
         } else if (!Intrinsics.areEqual(this.deducePhase, var2.deducePhase)) {
            return false;
         } else {
            return this.verbosity == var2.verbosity;
         }
      }
   }
}

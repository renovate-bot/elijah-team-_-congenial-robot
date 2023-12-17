package tripleo.elijah.nextgen.rosetta.DeducePhase;

//import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.rosetta.Rosetta;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.logging.ElLog;

//@Metadata(
//   mv = {1, 9, 0},
//   k = 1,
//   xi = 48,
//   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 #2\u00020\u0001:\u0001#B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J\t\u0010\u0019\u001a\u00020\nHÆ\u0003J7\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0006\u0010\u001b\u001a\u00020\rJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\t\u0010!\u001a\u00020\"HÖ\u0001R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006$"},
//   d2 = {"Ltripleo/elijah/nextgen/rosetta/DeducePhase/DeducePhase_deduceModule_Request;", "", "module", "Ltripleo/elijah/lang/i/OS_Module;", "listOfEvaFunctions", "", "Ltripleo/elijah/stages/gen_fn/EvaNode;", "verbosity", "Ltripleo/elijah/stages/logging/ElLog$Verbosity;", "deducePhase", "Ltripleo/elijah/stages/deduce/DeducePhase;", "(Ltripleo/elijah/lang/i/OS_Module;Ljava/lang/Iterable;Ltripleo/elijah/stages/logging/ElLog$Verbosity;Ltripleo/elijah/stages/deduce/DeducePhase;)V", "createdDeduceTypes2", "Ltripleo/elijah/stages/deduce/DeduceTypes2;", "getDeducePhase", "()Ltripleo/elijah/stages/deduce/DeducePhase;", "getListOfEvaFunctions", "()Ljava/lang/Iterable;", "getModule", "()Ltripleo/elijah/lang/i/OS_Module;", "getVerbosity", "()Ltripleo/elijah/stages/logging/ElLog$Verbosity;", "component1", "component2", "component3", "component4", "copy", "createDeduceTypes2", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "tripleo.elijah.core"}
//)
public final class DeducePhase_deduceModule_Request {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final OS_Module module;
   @NotNull
   private final Iterable listOfEvaFunctions;
   @NotNull
   private final ElLog.Verbosity verbosity;
   @NotNull
   private final DeducePhase deducePhase;
   @Nullable
   private DeduceTypes2 createdDeduceTypes2;

   public DeducePhase_deduceModule_Request(@NotNull OS_Module module, @NotNull Iterable listOfEvaFunctions, @NotNull ElLog.Verbosity verbosity, @NotNull DeducePhase deducePhase) {
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(listOfEvaFunctions, "listOfEvaFunctions");
      Intrinsics.checkNotNullParameter(verbosity, "verbosity");
      Intrinsics.checkNotNullParameter(deducePhase, "deducePhase");
//      super();
      this.module = module;
      this.listOfEvaFunctions = listOfEvaFunctions;
      this.verbosity = verbosity;
      this.deducePhase = deducePhase;
   }

   @NotNull
   public final OS_Module getModule() {
      return this.module;
   }

   @NotNull
   public final Iterable getListOfEvaFunctions() {
      return this.listOfEvaFunctions;
   }

   @NotNull
   public final ElLog.Verbosity getVerbosity() {
      return this.verbosity;
   }

   @NotNull
   public final DeducePhase getDeducePhase() {
      return this.deducePhase;
   }

   @NotNull
   public final DeduceTypes2 createDeduceTypes2() {
      DeduceTypes2Request deduceTypes2Request = new DeduceTypes2Request(this.module, this.deducePhase, this.verbosity);
      DeduceTypes2 var10000 = Rosetta.create(deduceTypes2Request);
      Intrinsics.checkNotNullExpressionValue(var10000, "create(...)");
      return var10000;
   }

   @NotNull
   public final OS_Module component1() {
      return this.module;
   }

   @NotNull
   public final Iterable component2() {
      return this.listOfEvaFunctions;
   }

   @NotNull
   public final ElLog.Verbosity component3() {
      return this.verbosity;
   }

   @NotNull
   public final DeducePhase component4() {
      return this.deducePhase;
   }

   @NotNull
   public final DeducePhase_deduceModule_Request copy(@NotNull OS_Module module, @NotNull Iterable listOfEvaFunctions, @NotNull ElLog.Verbosity verbosity, @NotNull DeducePhase deducePhase) {
      Intrinsics.checkNotNullParameter(module, "module");
      Intrinsics.checkNotNullParameter(listOfEvaFunctions, "listOfEvaFunctions");
      Intrinsics.checkNotNullParameter(verbosity, "verbosity");
      Intrinsics.checkNotNullParameter(deducePhase, "deducePhase");
      return new DeducePhase_deduceModule_Request(module, listOfEvaFunctions, verbosity, deducePhase);
   }

   // $FF: synthetic method
   public static DeducePhase_deduceModule_Request copy$default(DeducePhase_deduceModule_Request var0, OS_Module var1, Iterable var2, ElLog.Verbosity var3, DeducePhase var4, int var5, Object var6) {
      if ((var5 & 1) != 0) {
         var1 = var0.module;
      }

      if ((var5 & 2) != 0) {
         var2 = var0.listOfEvaFunctions;
      }

      if ((var5 & 4) != 0) {
         var3 = var0.verbosity;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.deducePhase;
      }

      return var0.copy(var1, var2, var3, var4);
   }

   @NotNull
   public String toString() {
      return "DeducePhase_deduceModule_Request(module=" + this.module + ", listOfEvaFunctions=" + this.listOfEvaFunctions + ", verbosity=" + this.verbosity + ", deducePhase=" + this.deducePhase + ")";
   }

   public int hashCode() {
      int result = this.module.hashCode();
      result = result * 31 + this.listOfEvaFunctions.hashCode();
      result = result * 31 + this.verbosity.hashCode();
      result = result * 31 + this.deducePhase.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DeducePhase_deduceModule_Request)) {
         return false;
      } else {
         DeducePhase_deduceModule_Request var2 = (DeducePhase_deduceModule_Request)other;
         if (!Intrinsics.areEqual(this.module, var2.module)) {
            return false;
         } else if (!Intrinsics.areEqual(this.listOfEvaFunctions, var2.listOfEvaFunctions)) {
            return false;
         } else if (this.verbosity != var2.verbosity) {
            return false;
         } else {
            return Intrinsics.areEqual(this.deducePhase, var2.deducePhase);
         }
      }
   }

//   @Metadata(
//      mv = {1, 9, 0},
//      k = 1,
//      xi = 48,
//      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
//      d2 = {"Ltripleo/elijah/nextgen/rosetta/DeducePhase/DeducePhase_deduceModule_Request$Companion;", "", "()V", "createDeduceTypes2Singleton", "Ltripleo/elijah/stages/deduce/DeduceTypes2;", "moduleRequest", "Ltripleo/elijah/nextgen/rosetta/DeducePhase/DeducePhase_deduceModule_Request;", "tripleo.elijah.core"}
//   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final DeduceTypes2 createDeduceTypes2Singleton(@NotNull DeducePhase_deduceModule_Request moduleRequest) {
         Intrinsics.checkNotNullParameter(moduleRequest, "moduleRequest");
         if (moduleRequest.createdDeduceTypes2 == null) {
            moduleRequest.createdDeduceTypes2 = moduleRequest.createDeduceTypes2();
         }

         DeduceTypes2 var10000 = moduleRequest.createdDeduceTypes2;
         Intrinsics.checkNotNull(var10000);
         return var10000;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}

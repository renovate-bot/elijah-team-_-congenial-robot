package tripleo.elijah.nextgen.rosetta.DeduceTypes2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request;
import tripleo.elijah.stages.deduce.DeducePhase;

@Metadata(
   mv = {1, 9, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"},
   d2 = {"Ltripleo/elijah/nextgen/rosetta/DeduceTypes2/DeduceTypes2_deduceFunctions_Request;", "", "request", "Ltripleo/elijah/nextgen/rosetta/DeducePhase/DeducePhase_deduceModule_Request;", "listOfEvaFunctions", "", "Ltripleo/elijah/stages/gen_fn/EvaNode;", "b", "", "deducePhase", "Ltripleo/elijah/stages/deduce/DeducePhase;", "(Ltripleo/elijah/nextgen/rosetta/DeducePhase/DeducePhase_deduceModule_Request;Ljava/lang/Iterable;ZLtripleo/elijah/stages/deduce/DeducePhase;)V", "getB", "()Z", "getDeducePhase", "()Ltripleo/elijah/stages/deduce/DeducePhase;", "getListOfEvaFunctions", "()Ljava/lang/Iterable;", "getRequest", "()Ltripleo/elijah/nextgen/rosetta/DeducePhase/DeducePhase_deduceModule_Request;", "tripleo.elijah.core"}
)
public final class DeduceTypes2_deduceFunctions_Request {
   @NotNull
   private final DeducePhase_deduceModule_Request request;
   @NotNull
   private final Iterable listOfEvaFunctions;
   private final boolean b;
   @NotNull
   private final DeducePhase deducePhase;

   public DeduceTypes2_deduceFunctions_Request(@NotNull DeducePhase_deduceModule_Request request, @NotNull Iterable listOfEvaFunctions, boolean b, @NotNull DeducePhase deducePhase) {
      Intrinsics.checkNotNullParameter(request, "request");
      Intrinsics.checkNotNullParameter(listOfEvaFunctions, "listOfEvaFunctions");
      Intrinsics.checkNotNullParameter(deducePhase, "deducePhase");
//      super();
      this.request = request;
      this.listOfEvaFunctions = listOfEvaFunctions;
      this.b = b;
      this.deducePhase = deducePhase;
   }

   @NotNull
   public final DeducePhase_deduceModule_Request getRequest() {
      return this.request;
   }

   @NotNull
   public final Iterable getListOfEvaFunctions() {
      return this.listOfEvaFunctions;
   }

   public final boolean getB() {
      return this.b;
   }

   @NotNull
   public final DeducePhase getDeducePhase() {
      return this.deducePhase;
   }
}

package tripleo.elijah.nextgen.rosetta.DeduceTypes2;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.sanaa.ElIntrinsics;

public final class DeduceTypes2_deduceFunctions_Request {
   @NotNull
   private final DeducePhase_deduceModule_Request request;
   @NotNull
   private final Iterable listOfEvaFunctions;
   private final boolean b;
   @NotNull
   private final DeducePhase deducePhase;

   public DeduceTypes2_deduceFunctions_Request(@NotNull DeducePhase_deduceModule_Request request, @NotNull Iterable listOfEvaFunctions, boolean b, @NotNull DeducePhase deducePhase) {
      ElIntrinsics.checkNotNullParameter(request, "request");
      ElIntrinsics.checkNotNullParameter(listOfEvaFunctions, "listOfEvaFunctions");
      ElIntrinsics.checkNotNullParameter(deducePhase, "deducePhase");

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

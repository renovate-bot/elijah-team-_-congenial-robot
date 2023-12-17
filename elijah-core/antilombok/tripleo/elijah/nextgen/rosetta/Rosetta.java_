package tripleo.elijah.nextgen.rosetta;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2Request;
import tripleo.elijah.nextgen.rosetta.DeduceTypes2.DeduceTypes2_deduceFunctions_Request;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;

public class Rosetta {
   private Rosetta() {
   }

   @Contract("_ -> new")
   public static @NotNull DeduceTypes2 create(DeduceTypes2Request aDeduceTypes2Request) {
      return new DeduceTypes2(aDeduceTypes2Request);
   }

   public static void create_call(@NotNull DeduceTypes2_deduceFunctions_Request rq) {
      if (rq == null) {
         $$$reportNull$$$0(0);
      }

      DeducePhase deducePhase = rq.getDeducePhase();
      deducePhase.__DeduceTypes2_deduceFunctions_Request__run(rq.getB(), rq.getRequest());
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "rq", "tripleo/elijah/nextgen/rosetta/Rosetta", "create_call"));
   }
}

package tripleo.elijah.nextgen.rosetta.DeduceTypes2;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.logging.ElLog;

public class DeduceTypes2Rosetta {
   private final DeduceTypes2Request request;

   public DeduceTypes2Rosetta(DeduceTypes2Request aRequest) {
      this.request = aRequest;
   }

   public OS_Module getModule() {
      return this.request.getModule();
   }

   public DeducePhase getDeducePhase() {
      return this.request.getDeducePhase();
   }

   public ErrSink getErrSink() {
      return this.request.getModule().getCompilation().getErrSink();
   }

   public ElLog createLog_DeduceTypes2() {
      return new ElLog(this.getModule().getFileName(), this.getVerbosity(), "DeduceTypes2");
   }

   private ElLog.@NotNull Verbosity getVerbosity() {
      ElLog.Verbosity var10000 = this.request.getVerbosity();
      if (var10000 == null) {
         $$$reportNull$$$0(0);
      }

      return var10000;
   }

   public ElLog createAndAddLog_DeduceTypes2() {
      ElLog log = this.createLog_DeduceTypes2();
      this.getDeducePhase().addLog(log);
      return log;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "tripleo/elijah/nextgen/rosetta/DeduceTypes2/DeduceTypes2Rosetta", "getVerbosity"));
   }
}

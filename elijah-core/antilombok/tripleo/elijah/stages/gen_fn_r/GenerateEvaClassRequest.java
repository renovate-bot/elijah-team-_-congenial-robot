package tripleo.elijah.stages.gen_fn_r;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.gen_fn.GenerateFunctions;

public final class GenerateEvaClassRequest {
   private final @NotNull GenerateFunctions generateFunctions;
   private final @NotNull ClassStatement classStatement;
   private final @NotNull ClassInvocation classInvocation;
   private final @NotNull RegisterClassInvocation_env passthruEnv;

   public GenerateEvaClassRequest(@NotNull GenerateFunctions generateFunctions, @NotNull ClassStatement classStatement, @NotNull ClassInvocation classInvocation, @NotNull RegisterClassInvocation_env passthruEnv) {
      Intrinsics.checkNotNullParameter(generateFunctions, "generateFunctions");
      Intrinsics.checkNotNullParameter(classStatement, "classStatement");
      Intrinsics.checkNotNullParameter(classInvocation, "classInvocation");
      Intrinsics.checkNotNullParameter(passthruEnv, "passthruEnv");
      this.generateFunctions = generateFunctions;
      this.classStatement = classStatement;
      this.classInvocation = classInvocation;
      this.passthruEnv = passthruEnv;
   }

   public final @NotNull GenerateFunctions getGenerateFunctions() {
      return this.generateFunctions;
   }

   public final @NotNull ClassStatement getClassStatement() {
      return this.classStatement;
   }

   public final @NotNull ClassInvocation getClassInvocation() {
      return this.classInvocation;
   }

   public final @NotNull RegisterClassInvocation_env getPassthruEnv() {
      return this.passthruEnv;
   }

   public final @NotNull GenerateFunctions component1() {
      return this.generateFunctions;
   }

   public final @NotNull ClassStatement component2() {
      return this.classStatement;
   }

   public final @NotNull ClassInvocation component3() {
      return this.classInvocation;
   }

   public final @NotNull RegisterClassInvocation_env component4() {
      return this.passthruEnv;
   }

   public final @NotNull GenerateEvaClassRequest copy(@NotNull GenerateFunctions generateFunctions, @NotNull ClassStatement classStatement, @NotNull ClassInvocation classInvocation, @NotNull RegisterClassInvocation_env passthruEnv) {
      Intrinsics.checkNotNullParameter(generateFunctions, "generateFunctions");
      Intrinsics.checkNotNullParameter(classStatement, "classStatement");
      Intrinsics.checkNotNullParameter(classInvocation, "classInvocation");
      Intrinsics.checkNotNullParameter(passthruEnv, "passthruEnv");
      return new GenerateEvaClassRequest(generateFunctions, classStatement, classInvocation, passthruEnv);
   }

   public static GenerateEvaClassRequest copy$default(GenerateEvaClassRequest var0, GenerateFunctions var1, ClassStatement var2, ClassInvocation var3, RegisterClassInvocation_env var4, int var5, Object var6) {
      if ((var5 & 1) != 0) {
         var1 = var0.generateFunctions;
      }

      if ((var5 & 2) != 0) {
         var2 = var0.classStatement;
      }

      if ((var5 & 4) != 0) {
         var3 = var0.classInvocation;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.passthruEnv;
      }

      return var0.copy(var1, var2, var3, var4);
   }

   public @NotNull String toString() {
      String var10000 = String.valueOf(this.generateFunctions);
      return "GenerateEvaClassRequest(generateFunctions=" + var10000 + ", classStatement=" + String.valueOf(this.classStatement) + ", classInvocation=" + String.valueOf(this.classInvocation) + ", passthruEnv=" + String.valueOf(this.passthruEnv) + ")";
   }

   public int hashCode() {
      int result = this.generateFunctions.hashCode();
      result = result * 31 + this.classStatement.hashCode();
      result = result * 31 + this.classInvocation.hashCode();
      result = result * 31 + this.passthruEnv.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof GenerateEvaClassRequest)) {
         return false;
      } else {
         GenerateEvaClassRequest var2 = (GenerateEvaClassRequest)other;
         if (!Intrinsics.areEqual(this.generateFunctions, var2.generateFunctions)) {
            return false;
         } else if (!Intrinsics.areEqual(this.classStatement, var2.classStatement)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.classInvocation, var2.classInvocation) ? false : Intrinsics.areEqual(this.passthruEnv, var2.passthruEnv);
         }
      }
   }
}

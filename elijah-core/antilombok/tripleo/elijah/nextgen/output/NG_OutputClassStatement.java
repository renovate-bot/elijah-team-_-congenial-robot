package tripleo.elijah.nextgen.output;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.sanaa.ElIntrinsics;
import tripleo.elijah.util.BufferTabbedOutputStream;

public final class NG_OutputClassStatement implements NG_OutputStatement {
   @NotNull
   private final BufferTabbedOutputStream __tos;
   @NotNull
   private final OS_Module aModuleDependency;
   @NotNull
   private final GenerateResult.TY ty;
   @NotNull
   private final String text;
   @NotNull
   private final Lazy moduleInput_$delegate;
   @NotNull
   private final Lazy moduleDependency$delegate;

   public NG_OutputClassStatement(@NotNull BufferTabbedOutputStream __tos, @NotNull OS_Module aModuleDependency, @NotNull GenerateResult.TY ty) {
      ElIntrinsics.checkNotNullParameter(__tos, "__tos");
      ElIntrinsics.checkNotNullParameter(aModuleDependency, "aModuleDependency");
      ElIntrinsics.checkNotNullParameter(ty, "ty");

      this.__tos = __tos;
      this.aModuleDependency = aModuleDependency;
      this.ty = ty;
      String var10001 = this.__tos.getBuffer().getText();
      ElIntrinsics.checkNotNullExpressionValue(var10001, "getText(...)");
      this.text = var10001;
      this.moduleInput_$delegate = LazyKt.lazy((Function0)(new Function0() {
         @NotNull
         public final EIT_ModuleInput invoke() {
            OS_Module m = NG_OutputClassStatement.this.getModuleDependency().getModule();
            return new EIT_ModuleInput(m, m.getCompilation());
         }
      }));
      this.moduleDependency$delegate = LazyKt.lazy((Function0)(new Function0() {
         @NotNull
         public final NG_OutDep invoke() {
            return new NG_OutDep(NG_OutputClassStatement.this.aModuleDependency);
         }
      }));
   }

   @NotNull
   public EX_Explanation getExplanation() {
      EX_Explanation var10000 = EX_Explanation.withMessage("NG_OutputClassStatement");
      ElIntrinsics.checkNotNullExpressionValue(var10000, "withMessage(...)");
      return var10000;
   }

   @NotNull
   public String getText() {
      return this.text;
   }

   @NotNull
   public GenerateResult.TY getTy() {
      return this.ty;
   }

   @NotNull
   public EIT_ModuleInput getModuleInput() {
      return this.getModuleInput_();
   }

   private final EIT_ModuleInput getModuleInput_() {
      Lazy var1 = this.moduleInput_$delegate;
      return (EIT_ModuleInput)var1.getValue();
   }

   private final NG_OutDep getModuleDependency() {
      Lazy var1 = this.moduleDependency$delegate;
      return (NG_OutDep)var1.getValue();
   }

   private final BufferTabbedOutputStream component1() {
      return this.__tos;
   }

   private final OS_Module component2() {
      return this.aModuleDependency;
   }

   private final GenerateResult.TY component3() {
      return this.ty;
   }

   @NotNull
   public final NG_OutputClassStatement copy(@NotNull BufferTabbedOutputStream __tos, @NotNull OS_Module aModuleDependency, @NotNull GenerateResult.TY ty) {
      ElIntrinsics.checkNotNullParameter(__tos, "__tos");
      ElIntrinsics.checkNotNullParameter(aModuleDependency, "aModuleDependency");
      ElIntrinsics.checkNotNullParameter(ty, "ty");
      return new NG_OutputClassStatement(__tos, aModuleDependency, ty);
   }

   // $FF: synthetic method
   public static NG_OutputClassStatement copy$default(NG_OutputClassStatement var0, BufferTabbedOutputStream var1, OS_Module var2, GenerateResult.TY var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.__tos;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.aModuleDependency;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.ty;
      }

      return var0.copy(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "NG_OutputClassStatement(__tos=" + this.__tos + ", aModuleDependency=" + this.aModuleDependency + ", ty=" + this.ty + ")";
   }

   public int hashCode() {
      int result = this.__tos.hashCode();
      result = result * 31 + this.aModuleDependency.hashCode();
      result = result * 31 + this.ty.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NG_OutputClassStatement)) {
         return false;
      } else {
         NG_OutputClassStatement var2 = (NG_OutputClassStatement)other;
         if (!ElIntrinsics.areEqual(this.__tos, var2.__tos)) {
            return false;
         } else if (!ElIntrinsics.areEqual(this.aModuleDependency, var2.aModuleDependency)) {
            return false;
         } else {
            return this.ty == var2.ty;
         }
      }
   }
}

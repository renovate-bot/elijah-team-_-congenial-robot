package tripleo.elijah.nextgen.output;

import kotlin.Lazy;
import kotlin.LazyKt;
//import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.util.BufferTabbedOutputStream;

//@Metadata(
//   mv = {1, 9, 0},
//   k = 1,
//   xi = 48,
//   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0016\u001a\u00020\u0003HÂ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÂ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÂ\u0003J'\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0010H\u0016J\b\u0010!\u001a\u00020\u0015H\u0016J\b\u0010\"\u001a\u00020\u0007H\u0016J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0015HÖ\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"},
//   d2 = {"Ltripleo/elijah/nextgen/output/NG_OutputClassStatement;", "Ltripleo/elijah/nextgen/output/NG_OutputStatement;", "__tos", "Ltripleo/elijah/util/BufferTabbedOutputStream;", "aModuleDependency", "Ltripleo/elijah/lang/i/OS_Module;", "ty", "Ltripleo/elijah/stages/gen_generic/GenerateResult$TY;", "(Ltripleo/elijah/util/BufferTabbedOutputStream;Ltripleo/elijah/lang/i/OS_Module;Ltripleo/elijah/stages/gen_generic/GenerateResult$TY;)V", "moduleDependency", "Ltripleo/elijah/nextgen/output/NG_OutDep;", "getModuleDependency", "()Ltripleo/elijah/nextgen/output/NG_OutDep;", "moduleDependency$delegate", "Lkotlin/Lazy;", "moduleInput_", "Ltripleo/elijah/nextgen/inputtree/EIT_ModuleInput;", "getModuleInput_", "()Ltripleo/elijah/nextgen/inputtree/EIT_ModuleInput;", "moduleInput_$delegate", "text", "", "component1", "component2", "component3", "copy", "equals", "", "other", "", "getExplanation", "Ltripleo/elijah/nextgen/outputstatement/EX_Explanation;", "getModuleInput", "getText", "getTy", "hashCode", "", "toString", "tripleo.elijah.core"}
//)
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
      Intrinsics.checkNotNullParameter(__tos, "__tos");
      Intrinsics.checkNotNullParameter(aModuleDependency, "aModuleDependency");
      Intrinsics.checkNotNullParameter(ty, "ty");
//      super();
      this.__tos = __tos;
      this.aModuleDependency = aModuleDependency;
      this.ty = ty;
      String var10001 = this.__tos.getBuffer().getText();
      Intrinsics.checkNotNullExpressionValue(var10001, "getText(...)");
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
      Intrinsics.checkNotNullExpressionValue(var10000, "withMessage(...)");
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
      Intrinsics.checkNotNullParameter(__tos, "__tos");
      Intrinsics.checkNotNullParameter(aModuleDependency, "aModuleDependency");
      Intrinsics.checkNotNullParameter(ty, "ty");
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
         if (!Intrinsics.areEqual(this.__tos, var2.__tos)) {
            return false;
         } else if (!Intrinsics.areEqual(this.aModuleDependency, var2.aModuleDependency)) {
            return false;
         } else {
            return this.ty == var2.ty;
         }
      }
   }
}
